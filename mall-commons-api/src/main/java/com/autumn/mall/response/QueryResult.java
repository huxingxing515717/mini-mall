/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.response
 * 文件名: QueryResult
 * 日期: 2020/3/14 16:32
 * 说明:
 */
package com.autumn.mall.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 查询结果
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult<T> {

    /**
     * 数据列表
     */
    private List<T> list;
    /**
     * 数据总数
     */
    private long total;
}