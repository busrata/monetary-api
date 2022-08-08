package com.maxijett.monetary.adapters;

import com.maxijett.monetary.cashbox.model.DriverTransaction;
import com.maxijett.monetary.cashbox.port.DriverTransactionPort;

public class DriverTransactionFakeDataAdapter implements DriverTransactionPort {

    @Override
    public Long createTransaction(DriverTransaction driverTransaction) {
        return DriverTransaction.builder()
                .id(1L)
                .driverId(driverTransaction.getDriverId())
                .dateTime(driverTransaction.getDateTime())
                .paymentCash(driverTransaction.getPaymentCash())
                .userId(driverTransaction.getUserId())
                .orderNumber(driverTransaction.getOrderNumber()).build()
                .getId();
    }
}
