package com.maxijett.monetary.adapters.store.jpa.entity;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "store_collection")
public class StoreCollectionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "cash", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal cash;

    @Column(name = "pos", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal pos;

    @Column(name = "tariffType", nullable = false)
    private TariffType tariffType;

    @Column(name = "client_id")
    private Long clientId;

    public StoreCollection toModel() {
        return StoreCollection.builder()
                .id(getId())
                .cash(getCash())
                .storeId(getStoreId())
                .clientId(getClientId())
                .pos(getPos())
                .tariffType(getTariffType()).build();
    }
}
