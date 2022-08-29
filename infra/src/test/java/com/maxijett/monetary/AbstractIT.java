package com.maxijett.monetary;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public abstract class AbstractIT {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected DriverPaymentTransactionRepository driverPaymentTransactionRepository;

    @LocalServerPort
    protected Integer port;

    protected void createDriverPaymentTransactionRecord(Long driverId, Long groupId, BigDecimal cashAmount, DriverEventType eventType, ZonedDateTime dateTime) {
        DriverPaymentTransactionEntity entity = new DriverPaymentTransactionEntity();
        entity.setDriverId(driverId);
        entity.setOrderNumber(RandomStringUtils.random(10));
        entity.setGroupId(groupId);
        entity.setDateTime(dateTime);
        entity.setEventType(eventType);
        entity.setPaymentCash(cashAmount);

        driverPaymentTransactionRepository.save(entity);
    }

}