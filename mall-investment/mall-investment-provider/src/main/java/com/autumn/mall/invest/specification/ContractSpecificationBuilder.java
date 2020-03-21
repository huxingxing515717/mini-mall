/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.specification
 * 文件名: ContractSpecificationBuilder
 * 日期: 2020/3/16 19:52
 * 说明:
 */
package com.autumn.mall.invest.specification;

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
 * @create 2020/3/16
 */
@Component
public class ContractSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null || (value instanceof List && ((List) value).isEmpty()))
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("serialNumber"), pattern), cb.like(root.get("signboard"), pattern));
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
        } else if ("buildingUuid".equals(property)) {
            return cb.equal(root.get("buildingUuid"), value);
        } else if ("floorUuid".equals(property)) {
            return cb.equal(root.get("floorUuid"), value);
        } else if ("positionUuid".equals(property)) {
            return cb.equal(root.get("positionUuid"), value);
        }
        return null;
    }
}