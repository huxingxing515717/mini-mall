/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.service
 * 文件名: SettleDetailServiceImpl
 * 日期: 2020/3/16 21:20
 * 说明:
 */
package com.autumn.mall.invest.service;

import cn.hutool.core.util.BooleanUtil;
import com.autumn.mall.commons.model.Order;
import com.autumn.mall.commons.model.OrderDirection;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.QueryResult;
import com.autumn.mall.invest.model.SettleDetail;
import com.autumn.mall.invest.repository.SettleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 结算明细业务层接口
 *
 * @author Anbang713
 * @create 2020/3/16
 */
@Service
public class SettleDetailServiceImpl implements SettleDetailService {

    @Autowired
    private SettleDetailRepository settleDetailRepository;

    @Override
    public Boolean existsNoStatement(String contractUuid, Date beginDate) {
        return settleDetailRepository.existsByContractUuidAndStatementUuidAndEndDateBefore(contractUuid, SettleDetail.NONE_STATEMENT, beginDate);
    }

    @Override
    @Transactional
    public void saveAll(String contractUuid, List<SettleDetail> details) {
        // 删掉再重新保存
        settleDetailRepository.deleteByContractUuid(contractUuid);
        settleDetailRepository.saveAll(details);
    }

    @Override
    public List<SettleDetail> findAllByContractUuid(String contractUuid) {
        return settleDetailRepository.findAllByContractUuid(contractUuid);
    }

    @Override
    public QueryResult<SettleDetail> query(QueryDefinition definition) {
        PageRequest pageRequest = getPageRequest(definition);
        Page<SettleDetail> page = settleDetailRepository.findAll(getSpecification(definition), pageRequest);

        QueryResult<SettleDetail> result = new QueryResult<>();
        result.setTotal(page.getTotalElements());
        result.getRecords().addAll(page.getContent());
        return result;
    }

    private PageRequest getPageRequest(QueryDefinition definition) {
        List<Order> orders = definition.getOrders();
        if (orders.isEmpty()) {
            orders.add(new Order("uuid", OrderDirection.asc));
        }
        List<Sort.Order> sortOrders = getOrders(orders);
        return PageRequest.of(definition.getCurrentPage(), definition.getPageSize(), Sort.by(sortOrders));
    }

    private Specification<SettleDetail> getSpecification(QueryDefinition definition) {
        return (Specification<SettleDetail>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Map<String, Object> params = definition.getFilter();
            for (String property : params.keySet()) {
                Object value = params.get(property);
                if (value == null || (value instanceof List && ((List) value).isEmpty())) {
                    continue;
                }
                if (Arrays.asList("storeUuid", "tenantUuid", "contractUuid", "subjectUuid").contains(property)) {
                    predicates.add(criteriaBuilder.equal(root.get(property), value));
                } else if ("noStatement".equals(property)) {
                    Object tempValue = value;
                    if (value instanceof List) {
                        tempValue = ((List) value).get(0);
                    }
                    if (BooleanUtil.toBoolean(tempValue.toString())) {
                        predicates.add(criteriaBuilder.notEqual(root.get("statementUuid"), SettleDetail.NONE_STATEMENT));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get("statementUuid"), SettleDetail.NONE_STATEMENT));
                    }
                }
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

    private List<Sort.Order> getOrders(List<Order> orders) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        for (Order order : orders) {
            String property = order.getProperty();
            if (Arrays.asList("store", "tenant", "contract", "subject").contains(property)) {
                sortOrders.add(new Sort.Order(Sort.Direction.valueOf(order.getDirection().name().toUpperCase()), property + "Uuid"));
            } else {
                sortOrders.add(new Sort.Order(Sort.Direction.valueOf(order.getDirection().name().toUpperCase()), property));
            }
        }
        return sortOrders;
    }
}