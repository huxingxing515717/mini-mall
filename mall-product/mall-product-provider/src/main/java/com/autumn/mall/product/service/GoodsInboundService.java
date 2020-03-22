/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.service
 * 文件名: GoodsInboundService
 * 日期: 2020/3/17 8:10
 * 说明:
 */
package com.autumn.mall.product.service;

import com.autumn.mall.commons.service.CacheService;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.product.model.GoodsInbound;
import com.autumn.mall.product.model.GoodsInboundDetail;

import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/17
 */
public interface GoodsInboundService extends CrudService<GoodsInbound>, CacheService {

    /**
     * 根据入库单id查询，结果按行号升序返回
     *
     * @param uuid
     * @return
     */
    List<GoodsInboundDetail> findDetailsByIdOrderByLineNumber(String uuid);

    /**
     * 生效入库单
     *
     * @param uuid
     */
    void doEffect(String uuid);
}