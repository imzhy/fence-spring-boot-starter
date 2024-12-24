package com.imzhy.fence.token;

import com.imzhy.fence.config.SecurityAccount;

import java.util.Optional;

/**
 * token 存储器
 * </br>
 * 要保证accessToken与refreshToken交集后依然具有唯一性
 * </br>
 * 实现类自己实现过期 token 清除
 *
 * @author zhy
 * @since 2024.12.22
 */
public interface TokenStore {
    /**
     * 存储 token
     *
     * @param token           token 信息
     * @param securityAccount 账户信息
     */
    void storeToken(Token token, SecurityAccount securityAccount);

    /**
     * 主动使指定账户 token 失效
     *
     * @param accountId 指定账户
     */
    void invalidateByAccount(Object accountId);

    /**
     * 主动使用指定 token 关联的信息失效
     *
     * @param token 指定 token
     */
    void invalidateByToken(String token);

    /**
     * 根据 accessToken 或 refreshToken 获取 token 信息
     *
     * @param token accessToken 或 refreshToken
     * @return token 信息
     */
    Optional<Token> getTokenByToken(String token);

    /**
     * 根据 accountId 获取 token 信息
     *
     * @param accountId accountId
     * @return token 信息
     */
    Optional<Token> getTokenByAccount(Object accountId);

    /**
     * 根据 accessToken 或 refreshToken 获取 账户 信息
     *
     * @param token accessToken 或 refreshToken
     * @return 账户信息
     */
    Optional<SecurityAccount> getAccountByToken(String token);

    /**
     * 根据 accountId 获取账户信息
     *
     * @param accountId accountId
     * @return 账户信息
     */
    Optional<SecurityAccount> getAccountByAccount(Object accountId);
}
