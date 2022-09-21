package com.maxijett.monetary.cashbox.model;

import com.maxijett.monetary.store.model.enumeration.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBox {
    private Long id;
    private Long groupId;
    private BigDecimal cash;
    private Long clientId;
    private Long userId;
    private RecordType recordType;

}
