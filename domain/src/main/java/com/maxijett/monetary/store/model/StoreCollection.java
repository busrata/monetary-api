package com.maxijett.monetary.store.model;

import com.maxijett.monetary.store.model.enumeration.TariffType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCollection {

    private Long id;
    private Long storeId;
    private Long groupId;
    private BigDecimal cash;
    private BigDecimal pos;
    private TariffType tariffType;
    private Long clientId;
    private BigDecimal balanceLimit;

    public StoreCashAndBalanceLimit toStoreCashAndBalanceLimit() {
        return StoreCashAndBalanceLimit.builder()
                .storeId(getStoreId())
                .cash(getCash())
                .balanceLimit(getBalanceLimit())
                .build();
    }
}
