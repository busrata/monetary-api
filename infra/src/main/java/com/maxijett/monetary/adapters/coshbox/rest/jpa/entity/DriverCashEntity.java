package com.maxijett.monetary.adapters.coshbox.rest.jpa.entity;

import com.maxijett.monetary.cashbox.model.DriverCash;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "driver_cash")
public class DriverCashEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name ="sequenceGenerator")
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "create_on")
    private Instant createOn;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "paying_account")
    private String payingAccount;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "prepaid_amount")
    private BigDecimal prepaidAmount;

    public DriverCash toModel() {
        return DriverCash.builder()
                .id(getId())
                .storeId(getStoreId())
                .groupId(getGroupId())
                .amount(getAmount())
                .createOn(getCreateOn())
                .clientId(getClientId())
                .driverId(getDriverId())
                .payingAccount(getPayingAccount())
                .prepaidAmount(getPrepaidAmount())
                .build();
    }

}
