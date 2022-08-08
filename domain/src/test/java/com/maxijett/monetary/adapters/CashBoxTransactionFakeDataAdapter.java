package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;

public class CashBoxTransactionFakeDataAdapter implements CashBoxTransactionPort {
    @Override
    public Long createTransaction(CashBoxTransaction cashBoxTransaction) {
        return CashBoxTransaction.builder().id(1L)
                .dateTime(cashBoxTransaction.getDateTime())
                .totalCash(cashBoxTransaction.getTotalCash())
                .driverId(cashBoxTransaction.getDriverId())
                .amount(cashBoxTransaction.getAmount()).build()
                .getId();
    }
}
