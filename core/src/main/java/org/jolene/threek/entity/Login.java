package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 所有可以登录的人都是Login
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
@Table(
        indexes = {@Index(columnList = "username", unique = true)},
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Login implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String username;
    @Column(length = 100)
    private String password;
    /**
     * 账户是否失效
     */
    private boolean accountNonExpired = true;
    /**
     * 账户是否锁定
     */
    private boolean accountNonLocked = true;
    /**
     * 证书是否过期
     */
    private boolean credentialsNonExpired = true;
    /**
     * 账户是否可用
     */
    private boolean enabled = true;

    /**
     * 头像路径,该值对应资源系统
     *
     * @see org.jolene.threek.service.ResourceService
     */
    @Column(length = 60)
    private String logoPath;
    @Column(length = 100)
    private String nickName;

    /**
     * 此登录创建时间
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdTime;
    /**
     * 如果非空表示到该时间前账户处于锁定状态
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime lockUntilTime;
    /**
     * 此登录最后登录时间
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime lastLoginTime;

    /**
     * @return 可阅读名字
     */
    public String getHumanReadName() {
        if (nickName != null)
            return nickName;
        return username;
    }

}
