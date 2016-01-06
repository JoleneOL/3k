package org.jolene.threek.common;

/**
 * @author Jolene
 */
public enum PaymentMethod {
    bank("银行支付"),
    alipay("支付宝"),
    wxpay("微信支付");

    private final String humanReadableName;

    PaymentMethod(String name) {
        humanReadableName = name;
    }

    public String getHumanReadableName() {
        return humanReadableName;
    }
}
