package com.maxijett.monetary.cashbox.usecase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBoxAmountGet implements UseCase {

    private Long clientId;

    private Long groupId;

    public static CashBoxAmountGet fromModel(Long clientId, Long groupId) {
        return CashBoxAmountGet.builder().clientId(clientId).groupId(groupId).build();

    }
}