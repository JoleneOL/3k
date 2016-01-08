package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * 入场券
 * <p>
 * 我们的用户界面还应该提供一个功能 供用户记录自己的入场券 酱紫就不用自己记录,并且避免在提供帮助时多输入一个东西
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class Ticket {

    @Id
    @Column(length = 25)
    private String id;
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdTime;
    /**
     * 认领者,认领以后就可随时使用;而且认领以后可以被他人再次认领;就是可以赠送;所以相关动作只能由认领者发起.
     */
    @ManyToOne
    private User claimant;
    /**
     * 认领时间,再次认领将再次变化
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime claimTime;
    private boolean used;
    /**
     * 谁使用了它
     */
    @ManyToOne
    private User useBy;
    @Column(columnDefinition = "datetime")
    private LocalDateTime useTime;

}
