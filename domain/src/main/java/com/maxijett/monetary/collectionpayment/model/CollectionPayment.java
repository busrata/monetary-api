package com.maxijett.monetary.collectionpayment.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CollectionPayment {

  private Long id;
  private Long storeId;
  private Long driverId;
  private BigDecimal cash;
  private BigDecimal pos;
  private ZonedDateTime date;
  private Long groupId;
  private Long clientId;

}
