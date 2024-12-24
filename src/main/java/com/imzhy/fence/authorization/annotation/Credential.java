package com.imzhy.fence.authorization.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 需要凭证，并且需要登录
 *
 * @author zhy
 * @since 2024.11.30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Auth
public @interface Credential {

    @AliasFor("expression")
    String value() default "";

    @AliasFor("value")
    String expression() default "";

    String[] roles() default {};

    String[] permissions() default {};
}
