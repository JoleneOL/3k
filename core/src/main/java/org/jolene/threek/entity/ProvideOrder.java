package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.entity.support.ProvideStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class ProvideOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 本金
     */
    private int invest;
    /**
     * 下单时的利率,就是从机制上说 是允许随时改变利率 而用户享受到的是下单时的利率
     */
    private double rate;
    /**
     * 下单时间
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime orderTime;

    private ProvideStatus orderStatus;
    /**
     * 拥有者
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

}
