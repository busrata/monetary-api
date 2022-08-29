package com.maxijett.monetary.cashbox.adapters;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxPort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CashBoxFakeDataAdapter implements CashBoxPort {

    public List<CashBox> boxes = new ArrayList<>();

    @Override
    public CashBox retrieve(Long groupId) {
        return CashBox.builder().id(1L).clientId(20000L).userId(2L).groupId(groupId).cash(BigDecimal.valueOf(250)).build();
    }

    @Override
    public List<CashBox> getListByClientId(Long clientId) {
        return List.of(
                CashBox.builder()
                        .clientId(20000L)
                        .cash(BigDecimal.valueOf(50))
                        .build(),
                CashBox.builder()
                        .clientId(20000L)
                        .cash(BigDecimal.valueOf(80))
                        .build(),
                CashBox.builder()
                        .clientId(20000L)
                        .cash(BigDecimal.valueOf(30))
                        .build()
        );
    }

    @Override
    public CashBox update(CashBox cashBox, CashBoxTransaction cashBoxTransaction) {
        boxes.add(cashBox);
        return cashBox;
    }
}
