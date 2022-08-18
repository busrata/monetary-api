package com.maxijett.monetary.adapters.store.jpa.entity;

import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "store_payment_transaction")
public class StorePaymentTransactionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "cash", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "pos", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal pos;

    @Column(name = "event_type", nullable = false)
    private StoreEventType eventType;

    @Column(name = "parent_transaction_id")
    private Long parentTransactionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "driver_id", nullable = false)
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
                .date(getDate())
                .eventType(getEventType())
                .orderNumber(getOrderNumber())
                .parentTransactionId(getParentTransactionId()).build();
    }
}
