package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CashBoxTransactionFakeDataAdapter implements CashBoxTransactionPort {

    private List<CashBoxTransaction> cashBoxTransactions = new ArrayList<>();

    @Override
    public Long createTransaction(CashBoxTransaction cashBoxTransaction) {

        CashBoxTransaction transaction = CashBoxTransaction.builder()
                .id(cashBoxTransaction.getId())
                .dateTime(cashBoxTransaction.getDateTime())
                .driverId(cashBoxTransaction.getDriverId())
                .amount(cashBoxTransaction.getAmount()).build();

        cashBoxTransactions.add(transaction);

        return cashBoxTransaction.getId();
    }

    public void assertContains(CashBoxTransaction cashBoxTransaction) {
        assertThat(cashBoxTransactions).containsAnyElementsOf(List.of(cashBoxTransaction));
    }

    public List<CashBoxTransaction> getCashBoxTransactions() {
        return cashBoxTransactions;
    }
}
