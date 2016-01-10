package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.feature.MutableTransferable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

/**
 * 平台帐号,所谓安全帐号
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class PlatformAccount implements MutableTransferable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 为了让管理方更好的选择安全帐号.
     */
    private boolean forbidden;

    private TransferableData transferableData = new TransferableData();

    @Override
    public String getContactNumber() {
        return transferableData.getContactNumber();
    }

    @Override
    public void setContactNumber(String contactNumber) {
        transferableData.setContactNumber(contactNumber);
    }

    @Override
    public String getContactPerson() {
        return transferableData.getContactPerson();
    }

    @Override
    public void setContactPerson(String contactPerson) {
        transferableData.setContactPerson(contactPerson);
    }

    @Override
    public Map<String, String> paymentDetails(PaymentMethod method) {
        return transferableData.paymentDetails(method);
    }

    @Override
    public void updatePaymentDetails(PaymentMethod method, Map<String, String> data) {
        transferableData.updatePaymentDetails(method, data);
    }
}
