package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.entity.support.RequirementStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 接受帮助订单
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class RequirementOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    /**
     * 下单时间
     */
    @Column(columnDefinition = "datetime")
    private LocalDateTime orderTime;
    /**
     * 拥有者
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

    private RequirementStatus requirementStatus;

    @OneToMany(mappedBy = "requirementOrder")
    private Set<MatchingOrder> matchingOrders;
}
