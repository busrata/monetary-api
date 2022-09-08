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
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;

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

    @Override
    @Transactional
    public CollectionPayment handle(CollectionPaymentCreate useCase) {

        CollectionPayment collectionPayment = collectionPaymentPort.create(useCase);

        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());

        driverCash.setCash(driverCash.getCash().subtract(useCase.getCash()));
        driverCashPort.update(driverCash, DriverPaymentTransaction.builder()
                .eventType(DriverEventType.DRIVER_PAY)
                .driverId(useCase.getDriverId())
                .paymentCash(useCase.getCash())
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .groupId(useCase.getGroupId())
            .build());

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        storeCollection.setCash(storeCollection.getCash().subtract(useCase.getCash()));
        storeCollectionPort.update(storeCollection, StorePaymentTransaction.builder()
                .driverId(useCase.getDriverId())
                .cash(useCase.getCash())
                .pos(BigDecimal.ZERO)
                .clientId(useCase.getClientId())
                .eventType(StoreEventType.DRIVER_PAY)
                .storeId(useCase.getStoreId())
                .createOn(ZonedDateTime.now(ZoneId.of("UTC")))
            .build());

        return collectionPayment;
    }

}
