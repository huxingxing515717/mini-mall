/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.service
 * 文件名: AbstractServiceImpl
 * 日期: 2020/3/14 19:09
 * 说明:
 */
package com.autumn.mall.commons.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.model.*;
import com.autumn.mall.commons.mq.Exchanges;
import com.autumn.mall.commons.mq.RoutingKeys;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.DefaultOrderBuilder;
import com.autumn.mall.commons.repository.OrderBuilder;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.commons.utils.RabbitMQUtils;
import com.autumn.mall.commons.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 业务层接口抽象实现类
 *
 * @author Anbang713
 * @create 2020/3/14
 */
public abstract class AbstractServiceImpl<T extends IsEntity> implements CrudService<T>, CacheService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RabbitMQUtils rabbitMQUtils;

    @Override
    @Transactional
    public String save(T entity) {
        doBeforeSave(entity);
        String actionName = "编辑";
        if (entity.getUuid() == null) {
            actionName = "新建";
            entity.setUuid(new IdWorker().nextId());
        }
        entity = getRepository().save(entity);
        saveOperationLog(entity.getUuid(), actionName);
        doAfterSave(entity);
        return entity.getUuid();
    }

    @Override
    @Transactional
    public void deleteById(String uuid) {
        getRepository().deleteById(uuid);
        doAfterDeleted(uuid);
    }

    @Override
    public T findById(String uuid) {
        // 查询缓存
        Object result = redisUtils.get(getCacheKey(uuid));
        if (result == null) {
            Optional<T> optional = getRepository().findById(uuid);
            if (optional.isPresent() == false) {
                MallExceptionCast.cast(CommonsResultCode.ENTITY_IS_NOT_EXIST);
            }
            // 放入缓存
            redisUtils.set(getCacheKey(uuid), optional.get(), RandomUtil.randomLong(3600, 86400));
            return optional.get();
        }
        return (T) result;
    }

    @Override
    public Map<String, T> findAllByUuids(Set<String> uuids) {
        if (CollectionUtil.isEmpty(uuids)) {
            return new HashMap<>();
        }
        Iterator<T> iterator = getRepository().findAllById(uuids).iterator();
        Map<String, T> result = new HashMap<>();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            result.put(entity.getUuid(), entity);
        }
        return result;
    }

    @Override
    public QueryResult<T> query(QueryDefinition definition) {
        PageRequest pageRequest = getPageRequest(definition);
        Page<T> page = getRepository().findAll(getSpecification(definition), pageRequest);

        QueryResult<T> result = new QueryResult<>();
        result.setTotal(page.getTotalElements());
        result.getRecords().addAll(page.getContent());
        return result;
    }

    private PageRequest getPageRequest(QueryDefinition definition) {
        List<Order> orders = definition.getOrders();
        if (orders.isEmpty()) {
            orders.add(new Order("uuid", OrderDirection.asc));
        }
        List<Sort.Order> sortOrders = new ArrayList<>();
        for (Order order : orders) {
            sortOrders.add(getOrderBuilder().build(order.getDirection(), order.getProperty()));
        }
        return PageRequest.of(definition.getCurrentPage(), definition.getPageSize(), Sort.by(sortOrders));
    }

    private Specification<T> getSpecification(QueryDefinition definition) {
        return new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Map<String, Object> params = definition.getFilter();
                for (String property : params.keySet()) {
                    Predicate predicate = getSpecificationBuilder().build(root, query, criteriaBuilder,
                            property, params.get(property));
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
                // 关键字查询
                if (StringUtils.isNotBlank(definition.getKeyword())) {
                    Predicate predicate = getSpecificationBuilder().build(root, query, criteriaBuilder,
                            "keyword", definition.getKeyword());
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }

        };
    }

    /**
     * 保存前操作
     *
     * @param entity
     */
    protected void doBeforeSave(T entity) {
        // do nothing
    }

    /**
     * 保存后操作
     *
     * @param entity
     */
    protected void doAfterSave(T entity) {
        // 更新缓存，key的过期时间为1天
        redisUtils.set(getCacheKey(entity.getUuid()), entity, RandomUtil.randomLong(3600, 86400));
    }

    public void saveOperationLog(String uuid, String actionName) {
        // 记录操作日志
        rabbitMQUtils.sendMsg(Exchanges.MALL_COMMONS_EXCHANGE, RoutingKeys.ENTITY_UPDATED, getOperationLog(uuid, actionName));
    }

    private Map<String, String> getOperationLog(String uuid, String actionName) {
        Map<String, String> log = new HashMap<>();
        log.put("entityKey", getCacheKey(uuid));
        log.put("time", DateUtil.now());
        log.put("operator", JSONUtil.toJsonStr(Admin.getDefaultUser()));
        log.put("actionName", actionName);
        return log;
    }

    public void doAfterDeleted(String uuid) {
        redisUtils.remove(getCacheKey(uuid));
    }

    private String getCacheKey(String uuid) {
        return getCacheKeyPrefix() + uuid;
    }

    public abstract BaseRepository<T> getRepository();

    public abstract SpecificationBuilder getSpecificationBuilder();

    public OrderBuilder getOrderBuilder() {
        return new DefaultOrderBuilder();
    }
}