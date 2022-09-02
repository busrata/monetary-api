package com.maxijett.monetary;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import com.maxijett.monetary.adapters.store.jpa.repository.StoreCollectionRepository;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
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

    @Autowired
    protected CollectionPaymentRepository collectionPaymentRepository;

    @Autowired
    protected StoreCollectionRepository storeCollectionRepository;

    @LocalServerPort
    protected Integer port;

    protected void createDriverPaymentTransactionRecord(Long driverId, Long groupId,
            BigDecimal cashAmount, DriverEventType eventType, ZonedDateTime dateTime) {
        DriverPaymentTransactionEntity entity = new DriverPaymentTransactionEntity();
        entity.setDriverId(driverId);
        entity.setOrderNumber(RandomStringUtils.random(10));
        entity.setGroupId(groupId);
        entity.setDateTime(dateTime);
        entity.setEventType(eventType);
        entity.setPaymentCash(cashAmount);

        driverPaymentTransactionRepository.save(entity);
    }

    protected void createCollectionPaymentRecord(Long driverId, Long storeId, Long clientId,
            Long groupId, BigDecimal cash, BigDecimal pos, ZonedDateTime dateTime) {
        CollectionPaymentEntity entity = new CollectionPaymentEntity();
        entity.setDriverId(driverId);
        entity.setClientId(clientId);
        entity.setStoreId(storeId);
        entity.setCreateOn(dateTime);
        entity.setCash(cash);
        entity.setPos(pos);
        entity.setGroupId(groupId);
        entity.setIsDeleted(false);

        collectionPaymentRepository.save(entity);
    }

    protected void createStoreCollectionRecord(Long storeId, Long groupId, BigDecimal cash,
            BigDecimal pos, TariffType tariffType, Long clientId) {
        StoreCollectionEntity entity = new StoreCollectionEntity();
        entity.setStoreId(storeId);
        entity.setGroupId(groupId);
        entity.setCash(cash);
        entity.setPos(pos);
        entity.setTariffType(tariffType);
        entity.setClientId(clientId);

        storeCollectionRepository.save(entity);
    }

}