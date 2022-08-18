package com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "collection_payment")
public class CollectionPaymentEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "cash", nullable = false,columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "pos", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal pos;

    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    public CollectionPayment toModel() {
        return CollectionPayment.builder()
                .id(getId())
                .cash(getCash())
                .clientId(getClientId())
                .date(getDate())
                .groupId(getGroupId())
                .driverId(getDriverId())
                .pos(getPos())
                .storeId(getStoreId())
                .build();
    }
}
