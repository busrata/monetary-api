package com.maxijett.monetary.adapters.cashbox.rest.jpa.entity;

import com.maxijett.monetary.driver.model.DriverCash;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "driver_cash")
@Entity(name = "DriverCashEntity")
public class DriverCashEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "cash", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "prepaid_collection_cash")
    private BigDecimal prepaidCollectionCash;

    public DriverCash toModel() {
        return DriverCash.builder()
                .id(getId())
                .clientId(getClientId())
                .groupId(getGroupId())
                .cash(getCash())
                .driverId(getDriverId())
                .prepaidCollectionCash(getPrepaidCollectionCash()).build();
    }
}

