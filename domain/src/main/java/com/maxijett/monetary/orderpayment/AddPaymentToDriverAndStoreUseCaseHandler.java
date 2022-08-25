package com.maxijett.monetary.orderpayment;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
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
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class AddPaymentToDriverAndStoreUseCaseHandler implements UseCaseHandler<Boolean, OrderPayment> {



  private final DriverPaymentTransactionPort driverPaymentTransactionPort;

  private final DriverCashPort driverCashPort;

  private final StorePaymentTransactionPort storePaymentTransactionPort;

  private final StoreCollectionPort storeCollectionPort;

  @Override
  @Transactional
  public Boolean handle(OrderPayment useCase) {

      StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

      if (useCase.getCash().compareTo(BigDecimal.ZERO) == 1) {

        DriverCash driverCash = driverCashPort.retrieve(useCase.getDriverId(),
            useCase.getGroupId());

        driverCash.setCash(driverCash.getCash().add(useCase.getCash()));

        driverCashPort.update(driverCash);

        driverPaymentTransactionPort.createTransaction(buildDriverPaymentTransaction(useCase));

        storeCollection.setCash(storeCollection.getCash().add(useCase.getCash()));

      }

      if (useCase.getPos().compareTo(BigDecimal.ZERO) == 1) {

        storeCollection.setPos(storeCollection.getPos().add(useCase.getPos()));
      }

      storePaymentTransactionPort.create(buildStorePaymentTransaction(useCase));

      storeCollectionPort.update(storeCollection);

      return true;

  }

  private DriverPaymentTransaction buildDriverPaymentTransaction(OrderPayment useCase){
    return DriverPaymentTransaction.builder()
        .groupId(useCase.getGroupId())
        .driverId(useCase.getDriverId())
        .paymentCash(useCase.getCash())
        .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
        .eventType(DriverEventType.PACKAGE_DELIVERED)
        .orderNumber(useCase.getOrderNumber())
        .build();

  }

  private StorePaymentTransaction buildStorePaymentTransaction(OrderPayment useCase){
    return StorePaymentTransaction.builder()
        .storeId(useCase.getStoreId())
        .clientId(useCase.getClientId())
        .pos(useCase.getPos())
        .cash(useCase.getCash())
        .date(ZonedDateTime.now(ZoneId.of("UTC")))
        .orderNumber(useCase.getOrderNumber())
        .eventType(StoreEventType.PACKAGE_DELIVERED)
        .build();
  }
}
