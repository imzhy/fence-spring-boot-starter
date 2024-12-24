package com.imzhy.fence.config;

import com.imzhy.fence.authentication.AbstractAuthenticator;
import com.imzhy.fence.default_impl.token.InMemoryTokenStore;
import com.imzhy.fence.token.TokenStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 配置适配器
 *
 * @author zhy
 * @since 2024.12.7
 */
public abstract class SecurityConfigureAdapter {

    private static final List<AbstractAuthenticator> authenticatorArr = new ArrayList<>();
    private static final TokenConfig tokenConfig = new TokenConfig();

    public static List<AbstractAuthenticator> getAuthenticatorArr() {
        return authenticatorArr;
    }

    public static TokenConfig getTokenConfig() {
        return tokenConfig;
    }

    public abstract void configure(Configure configure);

    public static class Configure {

        public void authenticator(AbstractAuthenticator authenticator) {
            SecurityConfigureAdapter.getAuthenticatorArr().add(authenticator);
        }

        public TokenConfig tokenConfig() {
            return tokenConfig;
        }
    }

    public static class TokenConfig {
        private int accessTokenLength = 128;
        private int refreshTokenLength = 128;
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
}
