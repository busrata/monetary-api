package com.maxijett.monetary.driver.adapters;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<DriverPaymentTransaction> retrieveTransactions(Long driverId, Long groupId, List<DriverEventType> eventTypes) {
        return Stream.of(
                DriverPaymentTransaction.builder()
                        .driverId(driverId)
                        .groupId(groupId)
                        .paymentCash(BigDecimal.valueOf(100.05))
                        .eventType(DriverEventType.PACKAGE_DELIVERED)
                        .dateTime(ZonedDateTime.now().minusHours(3))
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .build(),
                DriverPaymentTransaction.builder()
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .driverId(driverId)
                        .dateTime(ZonedDateTime.now().minusHours(2))
                        .eventType(DriverEventType.SUPPORT_ACCEPTED)
                        .paymentCash(BigDecimal.valueOf(48.02))
                        .groupId(groupId)
                        .build(),
                DriverPaymentTransaction.builder()
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .driverId(driverId)
                        .dateTime(ZonedDateTime.now().minusHours(1))
                        .eventType(DriverEventType.ADMIN_GET_PAID)
                        .paymentCash(BigDecimal.valueOf(76))
                        .groupId(groupId)
                        .build()
        ).filter(driverPaymentTransaction -> eventTypes.contains(driverPaymentTransaction.getEventType())).collect(Collectors.toList());
    }

    public List<DriverPaymentTransaction> getDriverPaymentTransactions() {
        return driverPaymentTransactions;
    }
}
