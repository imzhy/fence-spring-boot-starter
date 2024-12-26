package com.imzhy.fence.token;

import com.imzhy.fence.config.TokenConfig;
import com.imzhy.fence.util.DateUtils;
import com.imzhy.fence.util.RandomUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * token 相关
 *
 * @author zhy
 * @since 2024.12.22
 */
public class Token implements Serializable {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireTime;
    private Long refreshTokenExpireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(Long accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public Long getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Long refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public static Token build(TokenConfig tokenConfig) {
        Token token = new Token();
        if (Objects.isNull(tokenConfig)) tokenConfig = new TokenConfig();

        long time = DateUtils.getNowTimeStamp();

        // 生成 accessToken
        token.setAccessToken(RandomUtils.generateStr(tokenConfig.getAccessTokenLength()));
        if (Objects.nonNull(tokenConfig.getAccessTokenExpire())) {
            token.setAccessTokenExpireTime(time + tokenConfig.getAccessTokenExpire() * 1000);
        }

        // 生成 refreshToken
        token.setRefreshToken(RandomUtils.generateStr(tokenConfig.getRefreshTokenLength()));
        if (Objects.nonNull(tokenConfig.getRefreshTokenExpire())) {
            token.setRefreshTokenExpireTime(time + tokenConfig.getRefreshTokenExpire() * 1000);
        }

        return token;
    }

    public boolean isAccessTokenExpired() {
        if (Objects.isNull(this.accessTokenExpireTime)) return false;
        else return DateUtils.getNowTimeStamp() > this.accessTokenExpireTime;
    }

    public boolean isRefreshTokenExpired() {
        if (Objects.isNull(this.refreshTokenExpireTime)) return false;
        else return System.currentTimeMillis() > this.refreshTokenExpireTime;
    }
}
