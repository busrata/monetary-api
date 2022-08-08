package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.DriverTransaction;

public interface DriverTransactionPort {
    Long createTransaction(DriverTransaction driverTransaction);
}
