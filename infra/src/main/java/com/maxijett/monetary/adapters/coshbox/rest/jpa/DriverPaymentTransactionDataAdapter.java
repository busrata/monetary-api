package com.maxijett.monetary.adapters.coshbox.rest.jpa;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.coshbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.cashbox.model.DriverPaymentTransaction;
import com.maxijett.monetary.cashbox.model.enumeration.DriverEventType;
import com.maxijett.monetary.cashbox.port.DriverPaymentTransactionPort;
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

        return driverPaymentTransactionRepository.save(driverPaymentTransactionEntity).getId();
    }
}
