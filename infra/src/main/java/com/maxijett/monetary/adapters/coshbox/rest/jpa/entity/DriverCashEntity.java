package com.maxijett.monetary.adapters.coshbox.rest.jpa.entity;

import com.maxijett.monetary.cashbox.model.DriverCash;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "driver_cash")
public class DriverCashEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dispatch_driver_id")
    private Long dispatchDriverId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "cash")
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
                .dispatchDriverId(getDispatchDriverId())
                .prepaidCollectionCash(getPrepaidCollectionCash()).build();
    }
}
