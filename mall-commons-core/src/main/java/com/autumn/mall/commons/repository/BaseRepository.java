/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.repository
 * 文件名: BaseRepository
 * 日期: 2020/3/14 18:47
 * 说明:
 */
package com.autumn.mall.commons.repository;

import com.autumn.mall.commons.model.IsEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
@NoRepositoryBean
public interface BaseRepository<T extends IsEntity> extends CrudRepository<T, String>, JpaSpecificationExecutor<T> {

}