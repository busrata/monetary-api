package com.maxijett.monetary.orderpayment;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.VoidUseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class AddPaymentToDriverAndStoreUseCaseHandler implements VoidUseCaseHandler<OrderPayment> {

    private final DriverCashPort driverCashPort;

    private final StoreCollectionPort storeCollectionPort;

    @Override
    @Transactional
    public void handle(OrderPayment useCase) {

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        if (useCase.getCash().compareTo(BigDecimal.ZERO) > 0) {

            DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(),
                    useCase.getGroupId());

            driverCash.setCash(driverCash.getCash().add(useCase.getCash()));

            driverCashPort.update(driverCash, buildDriverPaymentTransaction(useCase));

            storeCollection.setCash(storeCollection.getCash().add(useCase.getCash()));

        }

        if (useCase.getPos().compareTo(BigDecimal.ZERO) > 0) {

            storeCollection.setPos(storeCollection.getPos().add(useCase.getPos()));
        }

        storeCollectionPort.update(storeCollection, buildStorePaymentTransaction(useCase));

    }

    private DriverPaymentTransaction buildDriverPaymentTransaction(OrderPayment useCase) {
        return DriverPaymentTransaction.builder()
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .paymentCash(useCase.getCash())
                .driverId(useCase.getDriverId())
                .groupId(useCase.getGroupId())
                .orderNumber(useCase.getOrderNumber())
                .eventType(DriverEventType.PACKAGE_DELIVERED)
                .build();
    }

    private StorePaymentTransaction buildStorePaymentTransaction(OrderPayment useCase) {
        return StorePaymentTransaction.builder()
                .storeId(useCase.getStoreId())
                .driverId(useCase.getDriverId())
                .pos(useCase.getPos())
                .cash(useCase.getCash())
                .clientId(useCase.getClientId())
                .eventType(StoreEventType.PACKAGE_DELIVERED)
                .orderNumber(useCase.getOrderNumber())
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
    }

}
