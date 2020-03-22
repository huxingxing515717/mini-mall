/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.service
 * 文件名: SalesInputServiceImpl
 * 日期: 2020/3/22 15:25
 * 说明:
 */
package com.autumn.mall.sales.service;

import cn.hutool.core.collection.CollectionUtil;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.IdWorker;
import com.autumn.mall.sales.model.SalesInput;
import com.autumn.mall.sales.model.SalesInputDetail;
import com.autumn.mall.sales.repository.SalesInputDetailRepository;
import com.autumn.mall.sales.repository.SalesInputRepository;
import com.autumn.mall.sales.response.SalesResultCode;
import com.autumn.mall.sales.specification.SalesInputSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 销售数据录入单业务层接口
 *
 * @author Anbang713
 * @create 2020/3/22
 */
@Service
public class SalesInputServiceImpl extends AbstractServiceImpl<SalesInput> implements SalesInputService {

    @Autowired
    private SalesInputRepository salesInputRepository;
    @Autowired
    private SalesInputDetailRepository salesInputDetailRepository;
    @Autowired
    private SalesInputSpecificationBuilder specificationBuilder;

    @Override
    public List<SalesInputDetail> findDetailsByUuid(String uuid) {
        return salesInputDetailRepository.findAllByInputUuidOrderByLineNumber(uuid);
    }

    @Override
    public void doEffect(String uuid) {

    }

    @Override
    protected void doBeforeSave(SalesInput entity) {
        super.doBeforeSave(entity);
        // 把商品为空的明细删除
        Iterator<SalesInputDetail> iterator = entity.getDetails().iterator();
        int lineNumber = 1;
        List<String> goodsUuids = new ArrayList<>();
        while (iterator.hasNext()) {
            SalesInputDetail detail = iterator.next();
            if (StringUtils.isBlank(detail.getGoodsUuid())) {
                iterator.remove();
            } else {
                detail.setLineNumber(lineNumber++);
                goodsUuids.add(detail.getGoodsUuid());
            }
        }
        if (CollectionUtil.isEmpty(entity.getDetails())) {
            MallExceptionCast.cast(SalesResultCode.GOODS_DETAILS_IS_EMPTY);
        }
        if (StringUtils.isBlank(entity.getUuid())) {
            entity.setBillNumber(new IdWorker().nextId());
        }
        entity.setGoodsUuids(CollectionUtil.join(goodsUuids, ","));
    }

    @Override
    protected void doAfterSave(SalesInput entity) {
        IdWorker idWorker = new IdWorker();
        entity.getDetails().stream().forEach(detail -> {
            if (StringUtils.isBlank(detail.getUuid())) {
                detail.setUuid(idWorker.nextId());
                detail.setInputUuid(entity.getUuid());
            }
        });
        salesInputDetailRepository.deleteByInputUuid(entity.getUuid());
        salesInputDetailRepository.saveAll(entity.getDetails());
        super.doAfterSave(entity);
    }

    @Override
    public void doAfterDeleted(String uuid) {
        salesInputDetailRepository.deleteByInputUuid(uuid);
        super.doAfterDeleted(uuid);
    }

    @Override
    public BaseRepository<SalesInput> getRepository() {
        return salesInputRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:sales:input:";
    }
}