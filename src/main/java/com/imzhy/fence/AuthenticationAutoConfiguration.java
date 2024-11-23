package com.imzhy.fence;

import com.imzhy.fence.filter.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 * @author zhy
 * @since 2024.11.23
 */
@Configuration
public class AuthenticationAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAutoConfiguration.class);

    public AuthenticationAutoConfiguration() {
        logger.debug("认证自动注入");
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        logger.debug("AuthenticationFilter 认证自动注入");
        return new AuthenticationFilter();
    }
}
