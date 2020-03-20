/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.specification
 * 文件名: GoodsSpecificationBuilder
 * 日期: 2020/3/15 20:33
 * 说明:
 */
package com.autumn.mall.product.specification;

import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Component
public class GoodsSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null)
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("code"), pattern), cb.like(root.get("name"), pattern));
        } else if ("usingState".equals(property)) {
            return cb.equal(root.get("usingState"), UsingState.valueOf(value.toString()));
        }
        return null;
    }
}