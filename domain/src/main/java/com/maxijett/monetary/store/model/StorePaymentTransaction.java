package com.maxijett.monetary.store.model;

import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorePaymentTransaction {

  private Long id;

  private String orderNumber;

  private ZonedDateTime createOn;

  private Long storeId;

  private BigDecimal cash;

  private BigDecimal pos;

  private StoreEventType eventType;

  private Long parentTransactionId;

  private Long userId;

  private Long driverId;

  private Long clientId;

}
