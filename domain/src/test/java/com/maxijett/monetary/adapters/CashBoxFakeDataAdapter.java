package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;

import java.math.BigDecimal;

public class CashBoxFakeDataAdapter implements CashBoxPort {
    @Override
    public CashBox retrive(Long groupId) {
        return CashBox.builder().id(1L).clientId(20000L).userId(2L).groupId(groupId).cash(BigDecimal.ZERO).build();
    }
}
