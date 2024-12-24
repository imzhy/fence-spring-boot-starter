package com.imzhy.fence.authorization.annotation;

import java.lang.annotation.*;

/**
 * 不需要任何权限
 *
 * @author zhy
 * @since 2024.11.30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Auth
public @interface Permit {
}
