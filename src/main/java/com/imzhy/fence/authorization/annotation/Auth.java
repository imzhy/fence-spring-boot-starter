package com.imzhy.fence.authorization.annotation;

import java.lang.annotation.*;

/**
 * 权限
 *
 * @author zhy
 * @since 2024.12.2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
