/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.sales.specification
 * 文件名: SalesInputSpecificationBuilder
 * 日期: 2020/3/22 15:19
 * 说明:
 */
package com.autumn.mall.sales.specification;

import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/22
 */
@Component
public class SalesInputSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null || (value instanceof List && ((List) value).isEmpty()))
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("code"), pattern), cb.like(root.get("name"), pattern));
        } else if ("state".equals(property)) {
            if (value instanceof List) {
                List<Predicate> predicates = new ArrayList<>();
                ((List) value).stream().forEach(val -> predicates.add(cb.equal(root.get("state"), BizState.valueOf(val.toString()))));
                return cb.or(predicates.toArray(new Predicate[]{}));
            } else {
                return cb.equal(root.get("state"), BizState.valueOf(value.toString()));
            }
        } else if ("storeUuid".equals(property)) {
            return cb.equal(root.get("storeUuid"), value);
        } else if ("tenantUuid".equals(property)) {
            return cb.equal(root.get("tenantUuid"), value);
        } else if ("contractUuid".equals(property)) {
            return cb.equal(root.get("contractUuid"), value);
        } else if ("goodsUuid".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.like(root.get("goodsUuids"), pattern);
        }
        return null;
    }
}