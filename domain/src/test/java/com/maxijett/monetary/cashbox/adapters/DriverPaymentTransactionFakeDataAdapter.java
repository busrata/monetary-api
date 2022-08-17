package com.maxijett.monetary.cashbox.adapters;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;

import java.util.ArrayList;
import java.util.List;

public class DriverPaymentTransactionFakeDataAdapter implements DriverPaymentTransactionPort {

    List<DriverPaymentTransaction> driverPaymentTransactions = new ArrayList<>();

    @Override
    public Long createTransaction(DriverPaymentTransaction driverTransaction) {

        DriverPaymentTransaction transaction = DriverPaymentTransaction.builder()
                .id(1L)
                .driverId(driverTransaction.getDriverId())
                .dateTime(driverTransaction.getDateTime())
                .paymentCash(driverTransaction.getPaymentCash())
                .userId(driverTransaction.getUserId())
                .eventType(driverTransaction.getEventType())
                .orderNumber(driverTransaction.getOrderNumber())
                .groupId(driverTransaction.getGroupId())
            .build();

        driverPaymentTransactions.add(transaction);

        return driverPaymentTransactions.get(0).getParentTransactionId();
    }

    public List<DriverPaymentTransaction> getDriverPaymentTransactions() {
        return driverPaymentTransactions;
    }
}
