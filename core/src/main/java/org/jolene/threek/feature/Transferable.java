package org.jolene.threek.feature;

import org.jolene.threek.common.PaymentMethod;

import java.util.Map;

/**
 * 可转账者
 *
 * @author Jolene
 */
public interface Transferable {
    /**
     * 获取指定支付方式的支付详情
     *
     * @param method 支付方式
     * @return 键值对的支付详情
     */
    Map<String, String> paymentDetails(PaymentMethod method);

    /**
     * 可选实现
     *
     * @return 联系人
     */
    String getContactPerson();

    /**
     * 可选实现
     *
     * @return 联系方式
     */
    String getContactNumber();
}
