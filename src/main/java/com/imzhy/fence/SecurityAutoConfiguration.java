package com.imzhy.fence;

import com.imzhy.fence.authorization.AuthorizationFilter;
import com.imzhy.fence.authentication.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 自动配置
 *
 * @author zhy
 * @since 2024.11.23
 */
@Configuration
public class SecurityAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAutoConfiguration.class);
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public SecurityAutoConfiguration(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        logger.debug("fence auto configuration");
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        logger.debug("AuthorizationFilter 认证自动注入");
        return new AuthorizationFilter(requestMappingHandlerMapping);
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        logger.debug("AuthenticationFilter 认证自动注入");
        return new AuthenticationFilter();
    }
}
