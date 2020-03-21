package com.autumn.mall.commons.service;

import com.autumn.mall.commons.model.IsEntity;
import com.autumn.mall.commons.model.QueryDefinition;
import com.autumn.mall.commons.response.QueryResult;

import java.util.Map;
import java.util.Set;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
public interface CrudService<T extends IsEntity> {

    /**
     * 新建实体，新增一条记录
     *
     * @param entity 实体
     * @return 新增记录的主键值
     */
    String save(T entity);

    /**
     * 根据主键uuid，删除一条记录
     *
     * @param uuid 主键uuid
     */
    void deleteById(String uuid);

    /**
     * 通过主键id，获取实体
     *
     * @param uuid 主键id
     * @return 实体
     */
    T findById(String uuid);

    /**
     * 通过主键集合获取实体，并将结果转换成map
     *
     * @param uuids
     * @return
     */
    Map<String, T> findAllByUuids(Set<String> uuids);

    /**
     * 根据关键字分页查询
     *
     * @param definition
     * @return
     */
    QueryResult<T> query(QueryDefinition definition);
}
