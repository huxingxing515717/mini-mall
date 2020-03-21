/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.specification
 * 文件名: GoodsInboundSpecificationBuilder
 * 日期: 2020/3/17 7:57
 * 说明:
 */
package com.autumn.mall.product.specification;

import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Anbang713
 * @create 2020/3/17
 */
@Component
public class GoodsInboundSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null)
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.like(root.get("billNumber"), pattern);
        } else if ("state".equals(property)) {
            return cb.equal(root.get("state"), BizState.valueOf(value.toString()));
        } else if ("warehouse".equals(property)) {
            return cb.equal(root.get("warehouse"), value);
        } else if ("goodsId".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.like(root.get("goodsIds"), pattern);
        }
        return null;
    }
}