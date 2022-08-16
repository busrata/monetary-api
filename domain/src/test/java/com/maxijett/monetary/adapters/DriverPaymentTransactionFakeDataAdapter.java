package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.DriverPaymentTransaction;
import com.maxijett.monetary.cashbox.port.DriverPaymentTransactionPort;

public class DriverPaymentTransactionFakeDataAdapter implements DriverPaymentTransactionPort {

    @Override
    public Long createTransaction(DriverPaymentTransaction driverTransaction) {
        return DriverPaymentTransaction.builder()
                .id(1L)
                .driverId(driverTransaction.getDriverId())
                .dateTime(driverTransaction.getDateTime())
                .paymentCash(driverTransaction.getPaymentCash())
                .userId(driverTransaction.getUserId())
                .orderNumber(driverTransaction.getOrderNumber()).build()
                .getId();
    }
}
