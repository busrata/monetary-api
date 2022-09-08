package com.maxijett.monetary.adapters.store.rest.jpa.entity;

import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Table(name = "store_payment_transaction")
@Entity(name = "StorePaymentTransactionEntity")
public class StorePaymentTransactionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "create_on", nullable = false)
    private ZonedDateTime createOn;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "cash", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "pos", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal pos;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreEventType eventType;

    @Column(name = "parent_transaction_id")
    private Long parentTransactionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    public StorePaymentTransaction toModel() {
        return StorePaymentTransaction.builder()
                .id(getId())
                .cash(getCash())
                .pos(getPos())
                .driverId(getDriverId())
                .clientId(getClientId())
                .storeId(getStoreId())
                .userId(getUserId())
                .createOn(getCreateOn())
                .eventType(getEventType())
                .orderNumber(getOrderNumber())
                .parentTransactionId(getParentTransactionId()).build();
    }
}
