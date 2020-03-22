/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.product.specification
 * 文件名: GoodsInboundSpecificationBuilder
 * 日期: 2020/3/17 7:57
 * 说明:
 */
package com.autumn.mall.product.specification;

import cn.hutool.core.date.DateUtil;
import com.autumn.mall.commons.model.BizState;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/17
 */
@Component
public class GoodsInboundSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null || (value instanceof List && ((List) value).isEmpty()))
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.like(root.get("billNumber"), pattern);
        } else if ("state".equals(property)) {
            if (value instanceof List) {
                List<Predicate> predicates = new ArrayList<>();
                ((List) value).stream().forEach(val -> predicates.add(cb.equal(root.get("state"), BizState.valueOf(val.toString()))));
                return cb.or(predicates.toArray(new Predicate[]{}));
            } else {
                return cb.equal(root.get("state"), BizState.valueOf(value.toString()));
            }
        } else if ("warehouse".equals(property)) {
            return cb.equal(root.get("warehouse"), value);
        } else if ("goodsUuid".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.like(root.get("goodsUuids"), pattern);
        } else if ("dateRange".equals(property)) {
            LinkedHashMap<String, String> valueMap = (LinkedHashMap) value;
            Date beginDate = DateUtil.parse(valueMap.get("beginDate"));
            Date endDate = DateUtil.parse(valueMap.get("endDate"));
            if (beginDate != null && endDate == null) {
                return cb.greaterThanOrEqualTo(root.get("inboundDate"), beginDate);
            } else if (beginDate == null && endDate != null) {
                return cb.lessThanOrEqualTo(root.get("inboundDate"), endDate);
            } else if (beginDate != null && endDate != null) {
                return cb.and(cb.greaterThanOrEqualTo(root.get("inboundDate"), beginDate), cb.lessThanOrEqualTo(root.get("inboundDate"), endDate));
            }
        }
        return null;
    }
}