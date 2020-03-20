package com.autumn.mall.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anbang713
 * @create 2020/3/14
 */
@Getter
@AllArgsConstructor
public enum UsingState {
    using("使用中"), disabled("已停用");

    private String caption;
}