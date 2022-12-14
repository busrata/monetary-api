package com.maxijett.monetary.store.model;

import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import com.maxijett.monetary.store.model.enumeration.RecordType;
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
    private Long groupId;
    private BigDecimal cash;
    private BigDecimal pos;
    private TariffType tariffType;
    private Long clientId;
    private BigDecimal balanceLimit;
    private WarmthType warmthType;
    private RecordType recordType;

    public StoreCashAndBalanceLimit toStoreCashAndBalanceLimit() {
        return StoreCashAndBalanceLimit.builder()
                .storeId(getStoreId())
                .cash(getCash())
                .balanceLimit(getBalanceLimit())
                .build();
    }
}
