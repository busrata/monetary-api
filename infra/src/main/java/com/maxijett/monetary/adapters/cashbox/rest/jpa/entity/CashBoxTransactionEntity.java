package com.maxijett.monetary.adapters.cashbox.rest.jpa.entity;

import com.maxijett.monetary.cashbox.model.enumaration.CashBoxEventType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "cash_box_transaction")
public class CashBoxTransactionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "paying_account")
    private String payingAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private CashBoxEventType eventType;
}
