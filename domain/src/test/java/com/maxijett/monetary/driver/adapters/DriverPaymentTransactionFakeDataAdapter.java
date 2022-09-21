package com.maxijett.monetary.driver.adapters;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                .cash(driverTransaction.getCash())
                .userId(driverTransaction.getUserId())
                .eventType(driverTransaction.getEventType())
                .orderNumber(driverTransaction.getOrderNumber())
                .groupId(driverTransaction.getGroupId())
                .clientId(driverTransaction.getClientId())
                .build();

        driverPaymentTransactions.add(transaction);

        return driverPaymentTransactions.get(0).getParentTransactionId();
    }

    @Override
    public Optional<DriverPaymentTransaction> findTransactionForRollback(String orderNumber, List<DriverEventType> eventTypes) {
        String rollbackOrderNumber = "999999999";
        if (orderNumber.equals(rollbackOrderNumber)) {
            return Optional.of(DriverPaymentTransaction.builder()
                    .id(1L)
                    .groupId(20L)
                    .dateTime(ZonedDateTime.now().minusHours(1))
                    .orderNumber(orderNumber)
                    .eventType(DriverEventType.PACKAGE_DELIVERED)
                    .cash(BigDecimal.valueOf(29.75))
                    .driverId(2L)
                    .clientId(17L)
                    .build());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<DriverPaymentTransaction> retrieveTransactions(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime, List<DriverEventType> eventTypes) {

        return Stream.of(
                DriverPaymentTransaction.builder()
                        .driverId(driverId)
                        .groupId(groupId)
                        .cash(BigDecimal.valueOf(100.05))
                        .eventType(DriverEventType.PACKAGE_DELIVERED)
                        .dateTime(ZonedDateTime.now().minusHours(3))
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .clientId(20000L)
                        .build(),
                DriverPaymentTransaction.builder()
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .driverId(driverId)
                        .dateTime(ZonedDateTime.now().minusHours(2))
                        .eventType(DriverEventType.SUPPORT_ACCEPTED)
                        .cash(BigDecimal.valueOf(48.02))
                        .groupId(groupId)
                        .clientId(20000L)
                        .build(),
                DriverPaymentTransaction.builder()
                        .orderNumber(String.valueOf(RandomUtils.nextLong()))
                        .driverId(driverId)
                        .dateTime(ZonedDateTime.now().minusHours(1))
                        .eventType(DriverEventType.ADMIN_GET_PAID)
                        .cash(BigDecimal.valueOf(76))
                        .groupId(groupId)
                        .clientId(20000L)
                        .build()
        ).filter(driverPaymentTransaction -> eventTypes.contains(driverPaymentTransaction.getEventType()) &&
                driverPaymentTransaction.getDateTime().isAfter(startTime) &&
                driverPaymentTransaction.getDateTime().isBefore(endTime)
        ).collect(Collectors.toList());
    }

    public List<DriverPaymentTransaction> getDriverPaymentTransactions() {
        return driverPaymentTransactions;
    }
}
