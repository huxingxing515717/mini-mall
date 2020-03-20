/**
 * 版权所有 (C), 2019-2020, XXX有限公司
 * 项目名：com.autumn.mall.commons.model
 * 文件名: Admin
 * 日期: 2020/3/14 20:42
 * 说明:
 */
package com.autumn.mall.commons.model;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * admin预定义用户
 *
 * @author Anbang713
 * @create 2020/3/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements IsEntity {
    private String uuid;
    private String loginName;
    private String displayName;
    private String password;

    public static Admin getDefaultUser() {
        return new Admin(StrUtil.uuid(), "admin", "系统管理员", "admin");
    }
}