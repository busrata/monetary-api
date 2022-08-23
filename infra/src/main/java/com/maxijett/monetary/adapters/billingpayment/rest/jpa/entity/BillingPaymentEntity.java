package com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name="BillingPaymentEntity")
@Table(name = "billing_payment")
public class BillingPaymentEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "paying_account", nullable = false)
    private String payingAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payload_type", nullable = false)
    private PayloadType payloadType;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name ="is_deleted", nullable = false)
    private Boolean isDeleted;

    public BillingPayment toModel() {
        return BillingPayment.builder()
                .id(getId())
                .paymentType(getPaymentType())
                .storeId(getStoreId())
                .clientId(getClientId())
                .amount(getAmount())
                .payloadType(getPayloadType())
                .isDeleted(getIsDeleted())
                .payingAccount(getPayingAccount()).build();
    }
}
