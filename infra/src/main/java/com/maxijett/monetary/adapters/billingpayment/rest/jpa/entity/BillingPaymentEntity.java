package com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity(name = "BillingPaymentEntity")
@Table(name = "billing_payment")
public class BillingPaymentEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "cash", nullable = false)
    private BigDecimal cash;

    @Column(name = "pos", nullable = false)
    private BigDecimal pos;

    @Column(name = "paying_account", nullable = false)
    private String payingAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payload_type", nullable = false)
    private PayloadType payloadType;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "create_on", nullable = false)
    private ZonedDateTime createOn;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    public BillingPayment toModel() {
        return BillingPayment.builder()
                .id(getId())
                .pos(getPos())
                .storeId(getStoreId())
                .clientId(getClientId())
                .cash(getCash())
                .payloadType(getPayloadType())
                .isDeleted(getIsDeleted())
                .payingAccount(getPayingAccount())
                .createOn(getCreateOn())
                .groupId(getGroupId()).build();
    }
}
