/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.repository
 * 文件名: GoodsInboundDetailRepository
 * 日期: 2020/3/17 8:08
 * 说明:
 */
package com.autumn.mall.product.repository;

import com.autumn.mall.product.model.GoodsInboundDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/17
 */
public interface GoodsInboundDetailRepository extends CrudRepository<GoodsInboundDetail, String> {

    /**
     * 根据入库单uuid查询，结果按行号升序返回
     *
     * @param goodsInboundUuid
     * @return
     */
    List<GoodsInboundDetail> findAllByGoodsInboundUuidOrderByLineNumber(String goodsInboundUuid);

    /**
     * 删除指定入库单uuid下的所有明细
     *
     * @param goodsInboundUuid
     */
    void deleteByGoodsInboundUuid(String goodsInboundUuid);
}