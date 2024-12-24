package com.imzhy.fence.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Objects;

/**
 * request 匹配器
 *
 * @author zhy
 * @since 2024.12.7
 */
public class RequestMatcher {

    private final HttpMethod httpMethod;
    private final PathPattern pathPattern;

    public RequestMatcher(String pattern, HttpMethod httpMethod) {
        pattern = StringUtils.isNotBlank(pattern) ? pattern.trim() : "";
        pathPattern = new PathPatternParser().parse(pattern);
        this.httpMethod = httpMethod;
    }

    /**
     * 请求是否匹配指定路径规则
     *
     * @param request 请求
     * @return 是否匹配
     */
    public boolean matches(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        PathContainer pathContainer = PathContainer.parsePath(requestURI);
        boolean matchURI = pathPattern.matches(pathContainer);

        boolean matchMethod = true;
        if (Objects.nonNull(httpMethod)) {
            matchMethod = httpMethod.matches(request.getMethod().toUpperCase());
        }

        return matchURI && matchMethod;
    }
}
