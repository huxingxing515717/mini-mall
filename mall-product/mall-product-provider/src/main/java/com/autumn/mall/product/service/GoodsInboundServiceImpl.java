/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.service
 * 文件名: GoodsInboundServiceImpl
 * 日期: 2020/3/17 8:10
 * 说明:
 */
package com.autumn.mall.product.service;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.product.model.GoodsInbound;
import com.autumn.mall.product.model.GoodsInboundDetail;
import com.autumn.mall.product.repository.GoodsInboundDetailRepository;
import com.autumn.mall.product.repository.GoodsInboundRepository;
import com.autumn.mall.product.specification.GoodsInboundSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品入库单业务层实现类
 *
 * @author Anbang713
 * @create 2020/3/17
 */
@Service
public class GoodsInboundServiceImpl extends AbstractServiceImpl<GoodsInbound> implements GoodsInboundService {

    @Autowired
    private GoodsInboundRepository goodsInboundRepository;
    @Autowired
    private GoodsInboundDetailRepository goodsInboundDetailRepository;
    @Autowired
    private GoodsInboundSpecificationBuilder specificationBuilder;

    @Override
    public List<GoodsInboundDetail> findDetailsByIdOrderByLineNumber(String id) {
        return goodsInboundDetailRepository.findAllByGoodsInboundIdOrderByLineNumber(id);
    }

    @Override
    public Map<String, String> doEffect(List<String> ids) {
        // TODO 生效后，商品入库，涉及分布式事务
        return null;
    }

    @Override
    protected void doAfterSave(GoodsInbound entity) {
        super.doAfterSave(entity);
        goodsInboundDetailRepository.saveAll(entity.getDetails());
    }

    @Override
    public void doAfterDeleted(String id) {
        super.doAfterDeleted(id);
        goodsInboundDetailRepository.deleteByGoodsInboundId(id);
    }

    @Override
    public BaseRepository<GoodsInbound> getRepository() {
        return goodsInboundRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:product:goodsinbound:";
    }
}