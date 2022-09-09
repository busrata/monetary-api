package com.maxijett.monetary.orderpayment;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.VoidUseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class AddPaymentToDriverAndStoreUseCaseHandler implements VoidUseCaseHandler<OrderPayment> {

    private final DriverCashPort driverCashPort;

    private final StoreCollectionPort storeCollectionPort;

    private final DriverPaymentTransactionPort driverPaymentTransactionPort;

    private final StorePaymentTransactionPort storePaymentTransactionPort;

    @Override
    @Transactional
    public void handle(OrderPayment useCase) {

        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());
        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());

        rollbackTransactionsForSupport(useCase.getOrderNumber(), useCase.getDriverId(), storeCollection, driverCash);

        if (useCase.getCash().compareTo(BigDecimal.ZERO) > 0) {

            driverCash.setCash(driverCash.getCash().add(useCase.getCash()));

            driverCashPort.update(driverCash, buildDriverPaymentTransaction(useCase));

            storeCollection.setCash(storeCollection.getCash().add(useCase.getCash()));

        }

        if (useCase.getPos().compareTo(BigDecimal.ZERO) > 0) {

            storeCollection.setPos(storeCollection.getPos().add(useCase.getPos()));
        }

        storeCollectionPort.update(storeCollection, buildStorePaymentTransaction(useCase));

    }

    private void rollbackTransactionsForSupport(String orderNumber, Long driverId, StoreCollection storeCollection, DriverCash driverCash) {
        driverPaymentTransactionPort.findTransactionForRollback(orderNumber, List.of(DriverEventType.PACKAGE_DELIVERED, DriverEventType.SUPPORT_ACCEPTED)).ifPresent(transaction -> {
            driverCash.setCash(driverCash.getCash().subtract(transaction.getPaymentCash()));
            driverCashPort.update(driverCash, buildDriverRollbackTransaction(transaction));
        });

        storePaymentTransactionPort.findTransactionForRollback(orderNumber, List.of(StoreEventType.PACKAGE_DELIVERED, StoreEventType.SUPPORT_ACCEPTED)).ifPresent(transaction -> {
            storeCollection.setCash(storeCollection.getCash().subtract(transaction.getCash()));
            storeCollection.setPos(storeCollection.getPos().subtract(transaction.getPos()));
            storeCollectionPort.update(storeCollection, buildStoreRollbackTransaction(orderNumber, driverId, transaction));

        });
    }

    private StorePaymentTransaction buildStoreRollbackTransaction(String orderNumber, Long driverId, StorePaymentTransaction transaction) {
        return StorePaymentTransaction.builder()
                .createOn(ZonedDateTime.now(ZoneOffset.UTC))
                .storeId(transaction.getStoreId())
                .pos(transaction.getPos())
                .cash(transaction.getCash())
                .driverId(driverId)
                .clientId(transaction.getClientId())
                .orderNumber(orderNumber)
                .eventType(StoreEventType.REFUND_OF_PAYMENT)
                .parentTransactionId(transaction.getId())
                .build();
    }

    private DriverPaymentTransaction buildDriverRollbackTransaction(DriverPaymentTransaction transaction) {
        return DriverPaymentTransaction.builder()
                .paymentCash(transaction.getPaymentCash())
                .eventType(DriverEventType.REFUND_OF_PAYMENT)
                .orderNumber(transaction.getOrderNumber())
                .dateTime(ZonedDateTime.now(ZoneOffset.UTC))
                .groupId(transaction.getGroupId())
                .driverId(transaction.getDriverId())
                .parentTransactionId(transaction.getId())
                .clientId(transaction.getClientId())
                .build();
    }

    private DriverPaymentTransaction buildDriverPaymentTransaction(OrderPayment useCase) {
        return DriverPaymentTransaction.builder()
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                .paymentCash(useCase.getCash())
                .driverId(useCase.getDriverId())
                .groupId(useCase.getGroupId())
                .orderNumber(useCase.getOrderNumber())
                .eventType(DriverEventType.PACKAGE_DELIVERED)
                .clientId(useCase.getClientId())
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
                .createOn(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
    }

}
