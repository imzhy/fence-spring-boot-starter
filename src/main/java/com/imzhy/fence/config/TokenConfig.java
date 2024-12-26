package com.imzhy.fence.config;

import com.imzhy.fence.default_impl.token.InMemoryTokenStore;
import com.imzhy.fence.token.TokenStore;

import java.util.Objects;

/**
 * Token配置
 *
 * @author zhy
 * @since 2024.12.26
 */
public class TokenConfig {
    private int accessTokenLength = 256;
    private int refreshTokenLength = 256;
    private Integer accessTokenExpire = 7200;
    private Integer refreshTokenExpire = 2592000;
    private TokenStore tokenStore = new InMemoryTokenStore();

    public int getAccessTokenLength() {
        return accessTokenLength;
    }

    public TokenConfig setAccessTokenLength(int accessTokenLength) {
        this.accessTokenLength = Math.max(accessTokenLength, 1);
        return this;
    }

    public int getRefreshTokenLength() {
        return refreshTokenLength;
    }

    public TokenConfig setRefreshTokenLength(int refreshTokenLength) {
        this.refreshTokenLength = Math.max(refreshTokenLength, 1);
        return this;
    }

    public Integer getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public TokenConfig setAccessTokenExpire(Integer accessTokenExpire) {
        this.accessTokenExpire = Objects.isNull(accessTokenExpire) ? null : Math.max(accessTokenExpire, 1);
        return this;
    }

    public Integer getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public TokenConfig setRefreshTokenExpire(Integer refreshTokenExpire) {
        this.refreshTokenExpire = Objects.isNull(refreshTokenExpire) ? null : Math.max(refreshTokenExpire, 1);
        return this;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public TokenConfig setTokenStore(TokenStore tokenStore) {
        if (Objects.nonNull(tokenStore)) this.tokenStore = tokenStore;
        return this;
    }
}
