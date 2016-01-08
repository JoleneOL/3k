package org.jolene.threek.entity;

import lombok.Getter;
import lombok.Setter;
import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.feature.MutableTransferable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 最终用户,会员
 *
 * @author Jolene
 */
@Entity
@Setter
@Getter
public class User extends Login implements MutableTransferable {
    /**
     * 余额
     */
    private double balance;
    private TransferableData transferableData = new TransferableData();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
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
    public String getContactNumber() {
        return transferableData.getContactNumber();
    }

    @Override
    public void setContactNumber(String contactNumber) {
        transferableData.setContactNumber(contactNumber);
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
