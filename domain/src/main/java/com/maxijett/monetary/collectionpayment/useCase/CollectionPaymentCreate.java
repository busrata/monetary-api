package com.maxijett.monetary.collectionpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionPaymentCreate implements UseCase {

  private Long storeId;
  private Long driverId;
  private BigDecimal cash;
  private BigDecimal pos;
  private ZonedDateTime date;
  private Long groupId;
  private Long clientId;

}
