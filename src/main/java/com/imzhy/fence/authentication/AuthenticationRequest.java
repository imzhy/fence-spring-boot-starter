package com.imzhy.fence.authentication;

import com.imzhy.fence.config.SecurityAccount;

/**
 * 认证使用到的 request 信息
 *
 * @author zhy
 * @since 2024.12.7
 */
public class AuthenticationRequest {

    private Object principal;
    private Object credential;
    private boolean authenticated = false;
    private SecurityAccount securityAccount;

    public AuthenticationRequest() {}

    public AuthenticationRequest(Object principal, Object credential) {
        this.principal = principal;
        this.credential = credential;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public Object getCredential() {
        return credential;
    }

    public void setCredential(Object credential) {
        this.credential = credential;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public SecurityAccount getSecurityAccount() {
        return securityAccount;
    }

    public void setSecurityAccount(SecurityAccount securityAccount) {
        this.securityAccount = securityAccount;
    }
}
