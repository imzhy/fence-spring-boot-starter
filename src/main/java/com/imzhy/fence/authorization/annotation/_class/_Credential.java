package com.imzhy.fence.authorization.annotation._class;

/**
 * 需要凭证，并且需要登录
 *
 * @author zhy
 * @since 2024.12.3
 */
public class _Credential extends _Auth {

    private String value;
    private String[] roles;
    private String[] permissions;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
