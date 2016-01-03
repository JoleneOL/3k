package org.jolene.threek.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 利息奖励
 *
 * @author Jolene
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestReward implements Serializable {

    private static final long serialVersionUID = 8210099225834825294L;

    /**
     * 从获得利息中获取奖励的利率
     */
    private double rate;
    /**
     * 直接推荐的人到达多少可以获得该级别的奖励
     */
    private int reach;
    /**
     * 从该级别最多可以获得多少人的奖励
     * 如果是小于等于0 则标识没有限制
     */
    private int max;
}
