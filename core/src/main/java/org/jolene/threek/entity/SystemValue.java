package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 纯字符串储存的系统配置,所有需要使用系统配置的类应该自己定义相关的静态字符串作为{@link #id}
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class SystemValue {

    @Id
    @Column(length = 50)
    private String id;
    private String value;

}
