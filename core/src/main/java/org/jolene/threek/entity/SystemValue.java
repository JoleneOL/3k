package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.entity.support.ResourceType;

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
    /**
     * 如果目标资源过于庞大,比如大于255 那么应该转存为资源,具体类型取决于此
     */
    private ResourceType resourceType = ResourceType.text;
    private String value;

}
