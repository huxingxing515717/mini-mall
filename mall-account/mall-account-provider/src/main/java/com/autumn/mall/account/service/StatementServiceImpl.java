/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.service
 * 文件名: StatementServiceImpl
 * 日期: 2020/3/23 21:24
 * 说明:
 */
package com.autumn.mall.account.service;

import com.autumn.mall.account.model.Statement;
import com.autumn.mall.account.model.StatementDetail;
import com.autumn.mall.account.repository.StatementRepository;
import com.autumn.mall.account.specification.StatementSpecificationBuilder;
import com.autumn.mall.commons.api.MallModuleKeyPrefixes;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账单业务层接口实现
 *
 * @author Anbang713
 * @create 2020/3/23
 */
@Service
public class StatementServiceImpl extends AbstractServiceImpl<Statement> implements StatementService {

    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private StatementSpecificationBuilder specificationBuilder;

    @Override
    public List<StatementDetail> findDetailsByUuid(String uuid) {
        return null;
    }

    @Override
    public void doEffect(String uuid) {

    }

    @Override
    public void doPay(String uuid) {

    }

    @Override
    public BaseRepository<Statement> getRepository() {
        return statementRepository;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return specificationBuilder;
    }

    @Override
    public String getCacheKeyPrefix() {
        return MallModuleKeyPrefixes.ACCOUNT_KEY_PREFIX_OF_STATEMENT;
    }
}