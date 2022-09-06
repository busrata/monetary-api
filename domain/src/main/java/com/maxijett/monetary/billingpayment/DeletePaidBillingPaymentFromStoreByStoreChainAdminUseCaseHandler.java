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
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainComponent
public class DeletePaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler implements
    UseCaseHandler<BillingPayment, BillingPaymentDelete> {

  private final BillingPaymentPort billingPaymentPort;

  private final StoreCollectionPort storeCollectionPort;

  private final CashBoxPort cashBoxPort;

  @Override
  public BillingPayment handle(BillingPaymentDelete useCase) {

    BillingPayment billingPayment = billingPaymentPort.retrieve(useCase.getId());

    billingPayment.setIsDeleted(true);

    billingPayment.setPayingAccount(useCase.getPayingAccount());

    if(useCase.getPayloadType().equals(PayloadType.COLLECTION)){

      StoreCollection storeCollection = storeCollectionPort.retrieve(billingPayment.getStoreId());

      if(billingPayment.getPaymentType().equals(PaymentType.CASH)) {
        storeCollection.setCash(storeCollection.getCash().add(billingPayment.getAmount()));


        CashBox cashBox = cashBoxPort.retrieve(storeCollection.getGroupId());
        cashBox.setCash(cashBox.getCash().add(billingPayment.getAmount()));
        cashBoxPort.update(cashBox, CashBoxTransaction.builder()
                .amount(billingPayment.getAmount())
                .cashBoxEventType(CashBoxEventType.REFUND_OF_PAYMENT)
                .payingAccount(useCase.getPayingAccount())
                .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
            .build());

      }

      else if(billingPayment.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
        storeCollection.setPos(storeCollection.getPos().add(billingPayment.getAmount()));

      }

      storeCollectionPort.update(storeCollection, StorePaymentTransaction.builder()
              .storeId(billingPayment.getStoreId())
              .cash(billingPayment.getPaymentType() == PaymentType.CASH ? billingPayment.getAmount() : BigDecimal.ZERO)
              .pos(billingPayment.getPaymentType() == PaymentType.CREDIT_CARD ? billingPayment.getAmount() : BigDecimal.ZERO)
              .clientId(billingPayment.getClientId())
              .eventType(StoreEventType.REFUND_OF_PAYMENT)
              .date(ZonedDateTime.now(ZoneId.of("UTC")))
          .build());



    }

    return billingPaymentPort.update(useCase.getId());

  }

}
