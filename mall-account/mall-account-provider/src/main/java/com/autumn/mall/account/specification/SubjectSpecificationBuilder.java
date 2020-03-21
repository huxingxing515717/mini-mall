/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.specification
 * 文件名: SubjectSpecificationBuilder
 * 日期: 2020/3/15 19:39
 * 说明:
 */
package com.autumn.mall.account.specification;

import com.autumn.mall.commons.model.UsingState;
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
 * @create 2020/3/15
 */
@Component
public class SubjectSpecificationBuilder implements SpecificationBuilder {

    @Override
    public Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value) {
        if (value == null)
            return null;
        if ("keyword".equals(property)) {
            String pattern = "%" + value + "%";
            return cb.or(cb.like(root.get("code"), pattern), cb.like(root.get("name"), pattern));
        } else if ("state".equals(property)) {
            if (value instanceof List) {
                List<Predicate> predicates = new ArrayList<>();
                ((List) value).stream().forEach(val -> predicates.add(cb.equal(root.get("state"), UsingState.valueOf(val.toString()))));
                return cb.or(predicates.toArray(new Predicate[]{}));
            } else {
                return cb.equal(root.get("state"), UsingState.valueOf(value.toString()));
            }
        }
        return null;
    }
}