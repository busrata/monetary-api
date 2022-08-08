package com.maxijett.monetary.adapters.coshbox.rest.dto;

import com.maxijett.monetary.cashbox.model.CashBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBoxDTO {
    private Long id;
    private Long groupId;
    private BigDecimal cash;
    private Long clientId;
    private Long userId;

    public static CashBoxDTO fromModel(CashBox cashBox){
        return CashBoxDTO.builder()
                .id(cashBox.getId())
                .clientId(cashBox.getClientId())
                .groupId(cashBox.getGroupId())
                .userId(cashBox.getUserId())
                .cash(cashBox.getCash())
                .build();
    }
}
