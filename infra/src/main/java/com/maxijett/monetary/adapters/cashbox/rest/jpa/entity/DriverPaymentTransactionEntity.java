package com.maxijett.monetary.adapters.cashbox.rest.jpa.entity;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Table(name = "driver_payment_transaction")
@Entity(name = "DriverPaymentTransactionEntity")
public class DriverPaymentTransactionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "cash", nullable = false)
    private BigDecimal cash;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_type", nullable = false)
    private DriverEventType eventType;

    @Column(name = "parent_transaction_id")
    private Long parentTransactionId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "client_id")
    private Long clientId;

    public DriverPaymentTransaction toModel() {
        return DriverPaymentTransaction.builder()
                .groupId(getGroupId())
                .cash(getCash())
                .driverId(getDriverId())
                .eventType(getEventType())
                .dateTime(getDateTime())
                .id(getId())
                .orderNumber(getOrderNumber())
                .userId(getUserId())
                .parentTransactionId(getParentTransactionId())
                .clientId(getClientId())
                .build();
    }
}
