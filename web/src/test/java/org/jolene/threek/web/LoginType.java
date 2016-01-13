package org.jolene.threek.web;

import org.jolene.threek.entity.Login;
import org.jolene.threek.entity.Manager;
import org.jolene.threek.entity.User;

/**
 * @author Jolene
 */
public enum LoginType {
    /**
     * 管理员
     */
    root(Manager.class, "ROOT"),
    /**
     * 服务
     */
    service(Manager.class, "SERVICE"),
    /**
     * 财务
     */
    finance(Manager.class, "FINANCE"),
    /**
     * 用户
     */
    user(User.class, "USER");

    private final Class<? extends Login> type;
    private final String role;

    LoginType(Class<? extends Login> type, String role) {
        this.type = type;
        this.role = role;
    }

    public Class<? extends Login> getType() {
        return type;
    }

    public String getRole() {
        return role;
    }
}
