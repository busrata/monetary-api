package com.maxijett.monetary.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCashAndBalanceLimit {

    private Long storeId;

    private BigDecimal cash;

    private BigDecimal balanceLimit;
}
