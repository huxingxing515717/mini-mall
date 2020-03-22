/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.account.controller
 * 文件名: SubjectController
 * 日期: 2020/3/15 19:43
 * 说明:
 */
package com.autumn.mall.account.controller;

import com.autumn.mall.account.client.SubjectApi;
import com.autumn.mall.account.model.Subject;
import com.autumn.mall.account.service.SubjectService;
import com.autumn.mall.commons.controller.AbstractSupportStateController;
import com.autumn.mall.commons.model.UsingState;
import com.autumn.mall.commons.service.CrudService;
import com.autumn.mall.commons.service.SupportStateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anbang713
 * @create 2020/3/15
 */
@Api(value = "科目管理")
@RestController
@RequestMapping("/subject")
public class SubjectController extends AbstractSupportStateController<Subject, UsingState> implements SubjectApi {

    @Autowired
    private SubjectService subjectService;

    @Override
    public SupportStateService<UsingState> getSupportStateService() {
        return subjectService;
    }

    @Override
    public CrudService<Subject> getCrudService() {
        return subjectService;
    }

    @Override
    public List<String> getSummaryFields() {
        return Arrays.asList(UsingState.using.name(), UsingState.disabled.name());
    }
}