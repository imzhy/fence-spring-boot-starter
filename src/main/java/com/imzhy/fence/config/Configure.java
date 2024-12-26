package com.imzhy.fence.config;

import com.imzhy.fence.authentication.AbstractAuthenticator;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类
 *
 * @author zhy
 * @since 2024.12.26
 */
public class Configure {

    private final List<AbstractAuthenticator> authenticatorArr = new ArrayList<>();
    private final TokenConfig tokenConfig = new TokenConfig();

    public List<AbstractAuthenticator> getAuthenticatorArr() {
        return this.authenticatorArr;
    }

    public TokenConfig getTokenConfig() {
        return this.tokenConfig;
    }

    public void authenticator(AbstractAuthenticator authenticator) {
        getAuthenticatorArr().add(authenticator);
    }
}
