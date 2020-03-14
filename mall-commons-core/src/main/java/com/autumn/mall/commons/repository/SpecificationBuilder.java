package com.autumn.mall.commons.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
public interface SpecificationBuilder {

    Predicate build(Root root, CriteriaQuery query, CriteriaBuilder cb, String property, Object value);
}
