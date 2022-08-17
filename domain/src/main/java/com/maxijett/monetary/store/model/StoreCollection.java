package com.maxijett.monetary.store.model;

import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCollection {

  private Long id;
  private Long storeId;
  private BigDecimal cash;
  private BigDecimal pos;
  private TariffType tariffType;
  private Long clientId;

}
