package com.maxijett.monetary.adapters.driver.rest.jpa;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverCashEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverCashRepository;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.port.DriverCashPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverCashDataAdapter implements DriverCashPort {

    private final DriverCashRepository driverCashRepository;

    private final DriverPaymentTransactionRepository driverPaymentTransactionRepository;

    @Override
    public DriverCash retrieve(Long driverId, Long groupId) {
        return driverCashRepository.findByDriverIdAndGroupId(driverId, groupId).map(DriverCashEntity::toModel)
                .orElseThrow(() -> new MonetaryApiBusinessException("monetaryapi.drivercash.notFound", String.valueOf(driverId), String.valueOf(groupId)));
    }

    @Override
    public DriverCash update(DriverCash driverCash, DriverPaymentTransaction driverPaymentTransaction) {

        driverPaymentTransactionRepository.save(fromModel(driverPaymentTransaction));
        return driverCashRepository.save(fromModel(driverCash)).toModel();
    }

    public List<DriverCash> getListByClientIdGreaterThanZero(Long clientId) {
        return driverCashRepository.findByClientIdAndCashGreaterThan(clientId, BigDecimal.ZERO).stream().map(DriverCashEntity::toModel).collect(
                Collectors.toList());
    }

    public List<DriverCash> getListByGroupIdGreaterThanZero(Long groupId) {
        return driverCashRepository.findByGroupIdAndCashGreaterThan(groupId, BigDecimal.ZERO).stream().map(DriverCashEntity::toModel).collect(
                Collectors.toList());

    }

    private DriverCashEntity fromModel(DriverCash driverCash) {
        DriverCashEntity entity = new DriverCashEntity();
        entity.setId(driverCash.getId());
        entity.setClientId(driverCash.getClientId());
        entity.setGroupId(driverCash.getGroupId());
        entity.setCash(Objects.isNull(driverCash.getCash()) ? BigDecimal.valueOf(0.00) : driverCash.getCash());
        entity.setDriverId(driverCash.getDriverId());
        entity.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash());
        return entity;
    }

    private DriverPaymentTransactionEntity fromModel(DriverPaymentTransaction driverPaymentTransaction) {
        DriverPaymentTransactionEntity entity = new DriverPaymentTransactionEntity();
        entity.setDriverId(driverPaymentTransaction.getDriverId());
        entity.setGroupId(driverPaymentTransaction.getGroupId());
        entity.setCash(driverPaymentTransaction.getCash());
        entity.setDateTime(driverPaymentTransaction.getDateTime());
        entity.setOrderNumber(driverPaymentTransaction.getOrderNumber());
        entity.setEventType(driverPaymentTransaction.getEventType());
        entity.setUserId(driverPaymentTransaction.getUserId());
        entity.setParentTransactionId(driverPaymentTransaction.getParentTransactionId());

        return entity;
    }
}
