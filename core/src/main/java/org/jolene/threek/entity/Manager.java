package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 管理层
 * <p>按照权限分配应该有
 * <ul>
 * <li>总站管理 ROOT</li>
 * <li>客服 SERVICE 匹配订单可以选择安全账户进行匹配 包括接受转账或者发起转账</li>
 * <li>财务专员 FINANCE 安全账户接受订单的话 具体执行确认或者上传凭证的操作;也可以查看报表</li>
 * </ul>
 * </p>
 *
 * @author Jolene
 */
@SuppressWarnings({"Convert2streamapi"})
@Entity
@Setter
@Getter
public class Manager extends Login {

    @ElementCollection
    @Column(length = 10)
    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null)
            return Collections.emptySet();
        HashSet<GrantedAuthority> authorities = new HashSet<>(roles.size());
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }

    private void markAsRole(boolean reset, String role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        if (reset)
            roles.clear();
        roles.add(role);
    }

    /**
     * 设置为客服
     *
     * @param reset 删除原权限
     */
    public void markAsService(boolean reset) {
        String role = "SERVICE";
        markAsRole(reset, role);
    }


    /**
     * 设置为财务
     *
     * @param reset 删除原权限
     */
    public void markAsFinance(boolean reset) {
        String role = "FINANCE";
        markAsRole(reset, role);
    }
}
