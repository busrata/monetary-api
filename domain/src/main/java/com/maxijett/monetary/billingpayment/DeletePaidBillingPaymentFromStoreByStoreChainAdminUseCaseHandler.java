package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDelete;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.model.enumaration.CashBoxEventType;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainComponent
public class DeletePaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler implements
    UseCaseHandler<BillingPayment, BillingPaymentDelete> {

  private final BillingPaymentPort billingPaymentPort;

  private final StorePaymentTransactionPort storePaymentTransactionPort;

  private final StoreCollectionPort storeCollectionPort;

  private final CashBoxPort cashBoxPort;

  private final CashBoxTransactionPort  cashBoxTransactionPort;

  @Override
  public BillingPayment handle(BillingPaymentDelete useCase) {

    BillingPayment billingPayment = billingPaymentPort.retrieve(useCase.getId());

    billingPayment.setIsDeleted(true);

    billingPayment.setPayingAccount(useCase.getPayingAccount());

    if(useCase.getPayloadType().equals(PayloadType.COLLECTION)){

      StoreCollection storeCollection = storeCollectionPort.retrieve(billingPayment.getStoreId());

      if(billingPayment.getPaymentType().equals(PaymentType.CASH)) {
        storeCollection.setCash(storeCollection.getCash().add(billingPayment.getAmount()));

        storePaymentTransactionPort.create(buildStorePaymentTransaction(billingPayment, billingPayment.getAmount(), BigDecimal.ZERO));

        CashBox cashBox = cashBoxPort.retrieve(storeCollection.getGroupId());
        cashBox.setCash(cashBox.getCash().add(billingPayment.getAmount()));
        cashBoxPort.update(cashBox);

        cashBoxTransactionPort.createTransaction(buildCashBoxTransaction(billingPayment.getAmount(),
            useCase.getPayingAccount()));

      }

      else if(billingPayment.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
        storeCollection.setPos(storeCollection.getPos().add(billingPayment.getAmount()));

        storePaymentTransactionPort.create(buildStorePaymentTransaction(billingPayment, BigDecimal.ZERO, billingPayment.getAmount()));
      }

      storeCollectionPort.update(storeCollection);



    }

    return billingPaymentPort.update(useCase.getId());

  }

  private StorePaymentTransaction buildStorePaymentTransaction(BillingPayment billingPayment, BigDecimal cash, BigDecimal pos){
    return StorePaymentTransaction.builder()
        .storeId(billingPayment.getStoreId())
        .clientId(billingPayment.getClientId())
        .pos(pos)
        .cash(cash)
        .eventType(StoreEventType.REFUND_OF_PAYMENT)
        .date(ZonedDateTime.now(ZoneId.of("UTC")))
        .build();

  }

  private CashBoxTransaction buildCashBoxTransaction(BigDecimal amount, String payingAccount){
    return CashBoxTransaction.builder()
        .amount(amount)
        .cashBoxEventType(CashBoxEventType.REFUND_OF_PAYMENT)
        .payingAccount(payingAccount)
        .build();
  }
}
