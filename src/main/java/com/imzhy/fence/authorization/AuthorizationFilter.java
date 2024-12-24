package com.imzhy.fence.authorization;

import com.imzhy.fence.authorization.annotation.Auth;
import com.imzhy.fence.authorization.annotation.Deny;
import com.imzhy.fence.authorization.annotation._class._Auth;
import com.imzhy.fence.authorization.handler.AccessForbiddenHandler;
import com.imzhy.fence.exception.authorization.AccessDeniedException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 认证过滤器
 *
 * @author zhy
 * @since 2024.11.23
 */
@Order(2)
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = "/*")
public class AuthorizationFilter implements Filter {

    private static final String PRE_FIGHT = "OPTIONS";

    private final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final AccessForbiddenHandler accessForbiddenHandler = (request, response, exception) -> {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
    };

    public AuthorizationFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 放通预检请求
        if (PRE_FIGHT.equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        HandlerExecutionChain handlerExecutionChain;
        try {
            handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (Objects.nonNull(handlerExecutionChain)) {
            Class<?> clz = ((HandlerMethod) handlerExecutionChain.getHandler()).getBeanType();
            Method method = ((HandlerMethod) handlerExecutionChain.getHandler()).getMethod();

            List<Annotation> clzAnnotations = Arrays.stream(clz.getAnnotations())
                    .filter(annotation -> annotation.annotationType().isAnnotationPresent(Auth.class))
                    .toList();
            List<Annotation> methodAnnotations = Arrays.stream(method.getAnnotations())
                    .filter(annotation -> annotation.annotationType().isAnnotationPresent(Auth.class))
                    .toList();
            List<Annotation> annotations = Stream.of(clzAnnotations, methodAnnotations).flatMap(List::stream).toList();

            try {
                List<_Auth> _authArr = convert(annotations);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!annotations.isEmpty()) {
                try {
                    List<Annotation> denyAnnotations = annotations.stream()
                            .filter(annotation -> annotation.annotationType() == Deny.class)
                            .toList();
                    if (!denyAnnotations.isEmpty()) {
                        throw new AccessDeniedException();
                    }
                } catch (AccessDeniedException accessDeniedException) {
                    this.accessForbiddenHandler.handler(request, response, accessDeniedException);
                    return;
                }
            } else {

            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private List<_Auth> convert(List<Annotation> annotations) throws Exception {
        List<_Auth> _authArr = new ArrayList<>();
        if (Objects.nonNull(annotations)) {
            for (Annotation annotation : annotations) {
                String annotationPackage = annotation.annotationType().getPackageName();
                String annotationName = annotation.annotationType().getSimpleName();
                Class<?> entityClass = Class.forName(MessageFormat.format("{0}._class._{1}", annotationPackage, annotationName));
                if (_Auth.class.isAssignableFrom(entityClass)) {
                    _Auth auth = (_Auth) entityClass.getDeclaredConstructor().newInstance();
                    annotationToObject(annotation, auth);
                    _authArr.add(auth);
                } else throw new RuntimeException("权限表构建失败");
            }
        }
        return _authArr;
    }

    private void annotationToObject(Annotation annotation, Object entityObject) throws Exception {
        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();

        for (Method method : annotationMethods) {
            Object value = method.invoke(annotation);
            String propertyName = method.getName();
            try {
                Method setter = entityObject.getClass().getMethod("set" + StringUtils.capitalize(propertyName), method.getReturnType());
                setter.invoke(entityObject, value);
            } catch (NoSuchMethodException ignored) {
            }
        }
    }
}
