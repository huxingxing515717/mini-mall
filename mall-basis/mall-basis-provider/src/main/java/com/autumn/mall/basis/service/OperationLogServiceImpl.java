/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.basis.service
 * 文件名: OperationLogServiceImpl
 * 日期: 2020/3/14 22:59
 * 说明:
 */
package com.autumn.mall.basis.service;

import com.autumn.mall.basis.model.OperationLog;
import com.autumn.mall.basis.repository.OperationLogRepository;
import com.autumn.mall.commons.exception.MallExceptionCast;
import com.autumn.mall.commons.repository.BaseRepository;
import com.autumn.mall.commons.repository.SpecificationBuilder;
import com.autumn.mall.commons.response.CommonsResultCode;
import com.autumn.mall.commons.service.AbstractServiceImpl;
import com.autumn.mall.commons.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
@Service
public class OperationLogServiceImpl extends AbstractServiceImpl<OperationLog> implements OperationLogService {

    @Autowired
    private OperationLogRepository operationLogRepository;

    @Override
    protected void doAfterSave(OperationLog entity) {
        // 注意，操作日志本身在保存成功后是不需要再往mq里发送消息的。
    }

    @Override
    public List<OperationLog> findAllByEntityKey(String entityKey) {
        if (StringUtils.isBlank(entityKey)) {
            MallExceptionCast.cast(CommonsResultCode.INVALID_PARAM);
        }
        return operationLogRepository.findAllByEntityKey(entityKey);
    }

    @Override
    @Transactional
    public String save(OperationLog entity) {
        if (entity.getId() == null) {
            entity.setId(new IdWorker().nextId());
        }
        entity = operationLogRepository.save(entity);
        return entity.getId();
    }

    @Override
    public BaseRepository<OperationLog> getRepository() {
        // 这里比较特殊
        return null;
    }

    @Override
    public SpecificationBuilder getSpecificationBuilder() {
        return null;
    }

    @Override
    public String getCacheKeyPrefix() {
        return "mall:basis:operationlog:";
    }
}