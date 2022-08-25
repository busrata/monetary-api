package com.maxijett.monetary.adapters.driver.jpa;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
