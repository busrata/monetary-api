package com.maxijett.monetary.driver.usecase;

import com.maxijett.monetary.common.model.UseCase;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverCashGet implements UseCase {

  private Long id;
  private Long driverId;
  private Long groupId;
  private BigDecimal cash;
  private Long clientId;
  private BigDecimal prepaidCollectionCash;

}