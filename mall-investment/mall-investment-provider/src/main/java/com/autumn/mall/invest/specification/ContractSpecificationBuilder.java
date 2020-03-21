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

/**
 * @author Anbang713
 * @create 2020/3/16
 */
@Component
public class ContractSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null)
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("serialNumber"), pattern), cb.like(root.get("signboard"), pattern));
        } else if ("bizState".equals(property)) {
            return cb.equal(root.get("bizState"), BizState.valueOf(value.toString()));
        } else if ("storeUuid".equals(property)) {
            return cb.equal(root.get("storeUuid"), value);
        } else if ("buildingUuid".equals(property)) {
            return cb.equal(root.get("buildingUuid"), value);
        } else if ("floorId".equals(property)) {
            return cb.equal(root.get("floorId"), value);
        } else if ("positionId".equals(property)) {
            return cb.equal(root.get("positionId"), value);
        }
        return null;
    }
}