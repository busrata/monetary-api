package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;


@DomainComponent
@RequiredArgsConstructor
public class PayCollectionPaymentToStoreByDriverUseCaseHandler implements
        UseCaseHandler<CollectionPayment, CollectionPaymentCreate> {


    private final CollectionPaymentPort collectionPaymentPort;

    private final DriverCashPort driverCashPort;

    private final StoreCollectionPort storeCollectionPort;

    private final DriverPaymentTransactionPort driverPaymentTransactionPort;

    private final StorePaymentTransactionPort storePaymentTransactionPort;

    @Override
    @Transactional
    public CollectionPayment handle(CollectionPaymentCreate useCase) {

        CollectionPayment collectionPayment = collectionPaymentPort.create(useCase);

        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());

        driverCash.setCash(driverCash.getCash().subtract(useCase.getCash()));
        driverCashPort.update(driverCash);
        driverPaymentTransactionPort.createTransaction(buildDriverPaymentTransaction(collectionPayment));

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        storeCollection.setCash(storeCollection.getCash().subtract(useCase.getCash()));
        storeCollectionPort.update(storeCollection);
        storePaymentTransactionPort.create(buildStorePaymentTransaction(collectionPayment));

        return collectionPayment;
    }

    private StorePaymentTransaction buildStorePaymentTransaction(CollectionPayment collectionPayment) {
        return StorePaymentTransaction.builder()
                .storeId(collectionPayment.getStoreId())
                .cash(collectionPayment.getCash())
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .clientId(collectionPayment.getClientId())
                .pos(BigDecimal.ZERO)
                .driverId(collectionPayment.getDriverId())
                .eventType(StoreEventType.DRIVER_PAY)
                .build();
    }

    private DriverPaymentTransaction buildDriverPaymentTransaction(CollectionPayment collectionPayment) {
        return DriverPaymentTransaction.builder()
                .driverId(collectionPayment.getDriverId())
                .paymentCash(collectionPayment.getCash())
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .eventType(DriverEventType.DRIVER_PAY)
                .groupId(collectionPayment.getGroupId())
                .build();
    }
}
