package org.jolene.threek.feature;

import org.jolene.threek.common.PaymentMethod;

import java.util.Map;

/**
 * 可变化的转账者.
 *
 * @author Jolene
 */
public interface MutableTransferable extends Transferable {

    /**
     * 可选实现
     *
     * @param contactPerson 联系人
     */
    void setContactPerson(String contactPerson);

    /**
     * 可选实现
     *
     * @param contactNumber 联系方式
     */
    void setContactNumber(String contactNumber);

    /**
     * 更新支付细节
     *
     * @param method 支付方式
     * @param data   如果是银行支付的话 必须包含<code>户名,银行名称,开户行,银行卡号</code>等键
     */
    void updatePaymentDetails(PaymentMethod method, Map<String, String> data);

}
