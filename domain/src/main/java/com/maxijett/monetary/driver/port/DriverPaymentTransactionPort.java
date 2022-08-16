package com.maxijett.monetary.driver.port;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;

public interface DriverPaymentTransactionPort {
    Long createTransaction(DriverPaymentTransaction driverTransaction);
}
