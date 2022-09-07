package com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity;

import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="CollectionReportEntity")
@Table(name="collection_report")
public class CollectionReportEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "order_number", nullable = false, unique = true)
  private String orderNumber;

  @Column(name = "store_id", nullable = false)
  private Long storeId;

  @Column(name="payment_date",nullable = false)
  private ZonedDateTime paymentDate;

  @Column(name="cash", nullable = false)
  private BigDecimal cash;

  @Column(name="pos", nullable = false)
  private BigDecimal pos;

  @Column(name="cash_commission",nullable = false)
  private BigDecimal cashCommission;

  @Column(name="pos_commission",nullable = false)
  private BigDecimal posCommission;

  @Column(name="distance_fee", nullable = false)
  private BigDecimal distanceFee;

  @Column(name="delivery_distance", nullable = false)
  private int deliveryDistance;

  @Column(name = "driver_id",nullable = false)
  private Long driverId;

  @Column(name = "group_id")
  private Long groupId;

  @Column(name ="warmth_type",nullable = false)
  @Enumerated(EnumType.STRING)
  private WarmthType warmthType;

  public CollectionReport toModel(){
    return CollectionReport.builder()
        .warmthType(getWarmthType())
        .pos(getPos())
        .cash(getCash())
        .orderNumber(getOrderNumber())
        .storeId(getStoreId())
        .paymentDate(getPaymentDate())
        .deliveryDistance(getDeliveryDistance())
        .distanceFee(getDistanceFee())
        .posCommission(getPosCommission())
        .cashCommission(getCashCommission())
        .groupId(getGroupId())
        .driverId(getDriverId())
        .id(getId())
        .build();
  }

}
