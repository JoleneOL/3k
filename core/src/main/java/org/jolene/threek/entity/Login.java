package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
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
