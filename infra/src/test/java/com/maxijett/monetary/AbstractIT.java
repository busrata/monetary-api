package com.maxijett.monetary;

import com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity.BillingPaymentEntity;
import com.maxijett.monetary.adapters.billingpayment.rest.jpa.repository.BillingPaymentRepository;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.adapters.store.rest.jpa.entity.StoreCollectionEntity;
import com.maxijett.monetary.adapters.store.rest.jpa.repository.StoreCollectionRepository;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
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

    @Autowired
    protected BillingPaymentRepository billingPaymentRepository;

    @LocalServerPort
    protected Integer port;

    protected void createDriverPaymentTransactionRecord(Long driverId, Long groupId, String orderNumber,
                                                        BigDecimal cashAmount, DriverEventType eventType, ZonedDateTime dateTime) {
        DriverPaymentTransactionEntity entity = new DriverPaymentTransactionEntity();
        entity.setDriverId(driverId);
        entity.setOrderNumber(orderNumber);
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
                                               BigDecimal pos, TariffType tariffType, Long clientId, BigDecimal balanceLimit) {
        StoreCollectionEntity entity = new StoreCollectionEntity();
        entity.setStoreId(storeId);
        entity.setGroupId(groupId);
        entity.setCash(cash);
        entity.setPos(pos);
        entity.setTariffType(tariffType);
        entity.setClientId(clientId);
        entity.setBalanceLimit(balanceLimit);

        storeCollectionRepository.save(entity);
    }

    protected void createBillingPaymentRecord(Long storeId, Long clientId, Long groupId, BigDecimal amount,
                                              ZonedDateTime dateTime, String payingAccount, PayloadType payloadType, PaymentType paymentType) {

        BillingPaymentEntity entity = new BillingPaymentEntity();
        entity.setClientId(clientId);
        entity.setStoreId(storeId);
        entity.setCreateOn(dateTime);
        entity.setAmount(amount);
        entity.setGroupId(groupId);
        entity.setPayingAccount(payingAccount);
        entity.setPayloadType(payloadType);
        entity.setPaymentType(paymentType);
        entity.setIsDeleted(false);

        billingPaymentRepository.save(entity);
    }

}
