package org.jolene.threek.web.model;

import lombok.Data;
import org.jolene.threek.common.PaymentMethod;

/**
 * 用户发起帮助请求时 提交的信息
 *
 * @author Jolene
 */
@Data
public class ProvideInfo {
    private int lot;
    private PaymentMethod[] paymentMethods;
}
