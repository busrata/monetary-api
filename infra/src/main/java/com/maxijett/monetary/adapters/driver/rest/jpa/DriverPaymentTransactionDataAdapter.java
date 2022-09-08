package com.maxijett.monetary.adapters.driver.rest.jpa;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverPaymentTransactionDataAdapter implements DriverPaymentTransactionPort {

    private final DriverPaymentTransactionRepository driverPaymentTransactionRepository;

    @Override
    public Long createTransaction(DriverPaymentTransaction from) {
        var driverPaymentTransactionEntity = new DriverPaymentTransactionEntity();
        driverPaymentTransactionEntity.setDriverId(from.getDriverId());
        driverPaymentTransactionEntity.setPaymentCash(from.getPaymentCash());
        driverPaymentTransactionEntity.setDateTime(from.getDateTime());
        driverPaymentTransactionEntity.setEventType(DriverEventType.ADMIN_GET_PAID);
        driverPaymentTransactionEntity.setGroupId(from.getGroupId());

        return driverPaymentTransactionRepository.save(driverPaymentTransactionEntity).getId();
    }

    @Override
    public Optional<DriverPaymentTransaction> findTransactionForRollback(String orderNumber, List<DriverEventType> eventTypes) {
        return driverPaymentTransactionRepository.findByOrderNumberAndEventTypeIn(orderNumber, eventTypes)
                .flatMap(transactions -> transactions.stream().max(Comparator.comparing(DriverPaymentTransactionEntity::getDateTime)))
                .map(DriverPaymentTransactionEntity::toModel);
    }

    @Override
    public List<DriverPaymentTransaction> retrieveTransactions(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime, List<DriverEventType> driverEventTypes) {
        return driverPaymentTransactionRepository.findAllByDriverIdAndGroupIdAndDateTimeBetweenAndEventTypeIn(driverId, groupId, startTime, endTime, driverEventTypes)
                .stream()
                .map(DriverPaymentTransactionEntity::toModel)
                .collect(Collectors.toList());
    }
}
