/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.invest.repository
 * 文件名: BuildingRepository
 * 日期: 2020/3/15 13:09
 * 说明:
 */
package com.autumn.mall.invest.repository;

import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.invest.model.Building;

import java.util.Optional;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
public interface BuildingRepository extends BaseRepository<Building> {

    Optional<Building> findByStoreUuidAndCode(String storeUuid, String code);
}