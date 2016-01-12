package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.entity.support.MatchingStatus;
import org.jolene.threek.feature.Transferable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * 匹配单
 * <p>
 * 无论是{@link #provideOrder 提供帮助订单}或者{@link #requirementOrder 需要帮助订单}都是可能为空的,但不可能同时为空.
 * <ul>
 * <li>当前者为空时:表示这是平台将提供的帮助,管理员或者客服可以在后台将相关凭据上传.</li>
 * <li>当后者为空时:表示这是平台使用{@link #platformAccount 安全账户}接受转账,</li>
 * </ul>
 * </p>
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class MatchingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MatchingStatus matchingStatus;
    /**
     * 上传的凭证资源,如果不为空就是需要收款方确认了
     */
    @Column(length = 60)
    private String voucherPath;
    @Column(columnDefinition = "datetime")
    private LocalDateTime uploadedTime;
    @Column(columnDefinition = "datetime")
    private LocalDateTime confirmTime;

    @ManyToOne
    private ProvideOrder provideOrder;
    @ManyToOne
    private RequirementOrder requirementOrder;
    @ManyToOne
    private PlatformAccount platformAccount;

    /**
     * 转账目的地
     *
     * @return 转账目的地
     */
    public Transferable getTransferee() {
        if (requirementOrder != null)
            return requirementOrder.getOwner();
        if (platformAccount != null)
            return platformAccount;
        throw new IllegalStateException("No Transferee to.");
    }

}
