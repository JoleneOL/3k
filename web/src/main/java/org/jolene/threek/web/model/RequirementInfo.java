package org.jolene.threek.web.model;

import lombok.Data;
import org.jolene.threek.common.PaymentMethod;

/**
 * 用户发起需要帮助时提交的信息
 *
 * @author Jolene
 */
@Data
public class RequirementInfo {
    private int amount;
    private PaymentMethod[] paymentMethods;
}
