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
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/17
 */
public interface GoodsInboundService extends CrudService<GoodsInbound>, CacheService {

    /**
     * 根据入库单id查询，结果按行号升序返回
     *
     * @param id
     * @return
     */
    List<GoodsInboundDetail> findDetailsByIdOrderByLineNumber(String id);

    /**
     * 批量生效
     *
     * @param ids
     * @return 返回生效失败的商品入库单错误原因
     */
    Map<String, String> doEffect(List<String> ids);
}