/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.repository
 * 文件名: SubjectRepository
 * 日期: 2020/3/15 19:40
 * 说明:
 */
package com.autumn.mall.account.repository;

import com.autumn.mall.account.model.Subject;
import com.autumn.mall.commons.repository.BaseRepository;

import java.util.Optional;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface SubjectRepository extends BaseRepository<Subject> {

    /**
     * 根据代码查找
     *
     * @param code
     * @return
     */
    Optional<Subject> findByCode(String code);
}