package com.maxijett.monetary.adapters.coshbox.rest.jpa.entity;


import com.maxijett.monetary.cashbox.model.CashBox;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cash_box")
public class CashBoxEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "cash", nullable = false)
    private BigDecimal cash;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public CashBox toModel() {
        return CashBox.builder()
                .id(getId())
                .userId(getUserId())
                .groupId(getGroupId())
                .clientId(getClientId())
                .cash(getCash())
                .build();
    }
}
