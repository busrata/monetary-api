package com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity;

import com.maxijett.monetary.collectionreport.model.CommissionConstantPos;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity(name = "CommissionConstantPosEntity")
@Table(name = "commission_constant_pos")

public class CommissionConstantPosEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    public CommissionConstantPos toModel() {
        return CommissionConstantPos.builder()
                .rate(getRate())
                .startTime(getStartTime())
                .endTime(getEndTime())
                .clientId(getClientId())
                .build();
    }

}
