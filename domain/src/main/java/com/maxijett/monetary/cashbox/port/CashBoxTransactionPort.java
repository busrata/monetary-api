package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.CashBoxTransaction;

public interface CashBoxTransactionPort {
    Long createTransaction(CashBoxTransaction cashBoxTransaction);
}
