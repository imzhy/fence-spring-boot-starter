package com.imzhy.fence.config;

import jakarta.annotation.Nonnull;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public interface SecurityAccount extends Serializable {

    @Nonnull
    Object getId();

    /**
     * 表示主体
     *
     * @return 主体
     */
    Object getPrincipal();

    /**
     * 用于验证主体身份
     *
     * @return 凭证
     */
    Object getCredential();

    /**
     * 账户是否被锁定
     *
     * @return 是否被锁定
     */
    default boolean isAccountLocked() {
        return false;
    }

    /**
     * 账户过期时间
     *
     * @return 过期时间，判断是否已过期
     */
    default Long getAccountExpiredTime() {
        return null;
    }

    /**
     * 凭证过期时间
     *
     * @return 过期时间，判断是否已过期
     */
    default Long getCredentialExpiredTime() {
        return null;
    }

    /**
     * 获取账户的权限
     *
     * @return 账户的权限
     */
    default Collection<String> getPermissions() {
        return new HashSet<>();
    }

    /**
     * 获取账户的角色
     *
     * @return 账户的角色
     */
    default Collection<String> getRoles() {
        return new HashSet<>();
    }
}
