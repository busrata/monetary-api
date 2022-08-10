package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.DriverPaymentTransaction;

public interface DriverPaymentTransactionPort {
    Long createTransaction(DriverPaymentTransaction driverTransaction);
}
