/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.response
 * 文件名: SummaryQueryResult
 * 日期: 2020/3/20 21:02
 * 说明:
 */
package com.autumn.mall.commons.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anbang713
 * @create 2020/3/20
 */
@Data
@NoArgsConstructor
public class SummaryQueryResult<T> extends QueryResult<T> {

    private Map<String, Object> summary = new HashMap<>();

    public static SummaryQueryResult newInstance(Object object) {
        if (object instanceof QueryResult == false)
            return null;

        QueryResult source = (QueryResult) object;
        SummaryQueryResult target = new SummaryQueryResult();

        target.setRecords(source.getRecords());
        if (source instanceof SummaryQueryResult) {
            target.setSummary(((SummaryQueryResult) source).getSummary());
        }
        return target;
    }
}