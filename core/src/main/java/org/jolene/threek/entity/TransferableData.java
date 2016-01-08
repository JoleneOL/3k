package org.jolene.threek.entity;

import lombok.Data;
import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.feature.MutableTransferable;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jolene
 */
@Embeddable
@Data
public class TransferableData implements MutableTransferable {

    @Column(length = 20)
    private String contactPerson;
    @Column(length = 22)
    private String contactNumber;

    @Column(length = 254)
    private String alipayAccount;
    @Column(length = 254)
    private String weixinAccount;
    // 户名 银行 帐号 开户行
    @Column(length = 20)
    private String bankAccountName;
    @Column(length = 34)
    private String bankAccount;
    @Column(length = 40)
    private String bankName;
    @Column(length = 60)
    private String bankDeposit;

    @Override
    public Map<String, String> paymentDetails(PaymentMethod method) {
        switch (method) {
            case wxpay:
                if (StringUtils.isEmpty(weixinAccount))
                    return null;
                return Collections.singletonMap("微信帐号", weixinAccount);
            case alipay:
                if (StringUtils.isEmpty(alipayAccount))
                    return null;
                return Collections.singletonMap("支付宝帐号", alipayAccount);
            default:
                if (StringUtils.isEmpty(bankAccountName)
                        || StringUtils.isEmpty(bankName)
                        || StringUtils.isEmpty(bankDeposit)
                        || StringUtils.isEmpty(bankAccount))
                    return null;
                HashMap<String, String> data = new HashMap<>();
                data.put("户名", bankAccountName);
                data.put("银行名称", bankName);
                data.put("开户行", bankDeposit);
                data.put("银行卡号", bankAccount);
                return data;
        }
    }

    @Override
    public void updatePaymentDetails(PaymentMethod method, Map<String, String> data) {
        switch (method) {
            case wxpay:
                setWeixinAccount(data.values().stream().findAny().get());
                break;
            case alipay:
                setAlipayAccount(data.values().stream().findAny().get());
                break;
            default:
                setBankAccount(data.get("银行卡号"));
                setBankAccountName(data.get("户名"));
                setBankDeposit(data.get("开户行"));
                setBankName(data.get("银行名称"));
        }
    }
}
