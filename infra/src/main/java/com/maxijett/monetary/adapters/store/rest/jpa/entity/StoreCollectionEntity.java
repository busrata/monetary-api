package com.maxijett.monetary.adapters.store.rest.jpa.entity;

import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.RecordType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "store_collection")
@Entity(name = "StoreCollectionEntity")
public class StoreCollectionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "cash", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "pos", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal pos;

    @Column(name = "tariff_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TariffType tariffType;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "balance_limit", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal balanceLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "warmth_type", nullable = false)
    private WarmthType warmthType;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type",nullable = false)
    private RecordType recordType;

    //!!morning sgift ve night shift

    public StoreCollection toModel() {
        return StoreCollection.builder()
                .id(getId())
                .cash(getCash())
                .storeId(getStoreId())
                .groupId(getGroupId())
                .clientId(getClientId())
                .pos(getPos())
                .tariffType(getTariffType())
                .balanceLimit(getBalanceLimit())
                .warmthType(getWarmthType())
                .build();
    }
}
