package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * 邮件正文
 *
 * @author Jolene
 */
@Entity
@Getter
@Setter
public class EmailContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 拥有者,发件人
     */
    @ManyToOne(optional = false)
    private Login belong;
    /**
     * 已发送
     */
    private boolean sent;
    /**
     * 如果设置为已删除,那么当所有相关email被删除时,它也会被删除,否者只是隐藏.
     */
    private boolean deleted;
    /**
     * 收件人
     */
    @ElementCollection
    @Column(length = 50)
    private Set<String> recipients;

    @Column(length = 100)
    private String title;
    @Lob
    private String content;
}
