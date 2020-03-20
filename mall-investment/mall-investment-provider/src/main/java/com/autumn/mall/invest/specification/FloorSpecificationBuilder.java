/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.specification
 * 文件名: FloorSpecificationBuilder
 * 日期: 2020/3/15 14:13
 * 说明:
 */
package com.autumn.mall.invest.specification;

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
public class FloorSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null)
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("code"), pattern), cb.like(root.get("name"), pattern));
        } else if ("state".equals(property)) {
            return cb.equal(root.get("state"), UsingState.valueOf(value.toString()));
        } else if ("storeUuid".equals(property)) {
            return cb.equal(root.get("storeUuid"), value);
        } else if ("buildingId".equals(property)) {
            return cb.equal(root.get("buildingId"), value);
        }
        return null;
    }
}