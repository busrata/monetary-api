package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler implements
        UseCaseHandler<BillingPayment, BillingPaymentCreate> {

    private static final ZoneId ISTANBUL_ZONE_ID = ZoneId.of("Europe/Istanbul");

    private final CashBoxPort cashBoxPort;

    private final BillingPaymentPort billingPaymentPort;

    private final StoreCollectionPort storeCollectionPort;

    private final StorePaymentTransactionPort storePaymentTransactionPort;

    private final CashBoxTransactionPort cashBoxTransactionPort;

    @Override
    public BillingPayment handle(BillingPaymentCreate useCase) {

        BillingPayment billingPayment = billingPaymentPort.create(useCase);
        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        if (useCase.getPayloadType().equals(PayloadType.COLLECTION)) {
            if (useCase.getPaymentType().equals(PaymentType.CASH)) {
                CashBox cashBox = cashBoxPort.retrieve(storeCollection.getGroupId());
                cashBox.setCash(cashBox.getCash().subtract(useCase.getAmount()));
                cashBoxTransactionPort.createTransaction(CashBoxTransaction.builder()
                        .dateTime(ZonedDateTime.now().withZoneSameInstant(ISTANBUL_ZONE_ID))
                        .amount(useCase.getAmount())
                        .payingAccount(useCase.getPayingAccount())
                        .build());

                storeCollection.setCash(storeCollection.getCash().subtract(useCase.getAmount()));
                storePaymentTransactionPort.create(StorePaymentTransaction.builder()
                        .storeId(useCase.getStoreId())
                        .cash(useCase.getAmount())
                        .clientId(useCase.getClientId())
                        .eventType(StoreEventType.ADMIN_GET_PAID)
                        .date(ZonedDateTime.now().withZoneSameInstant(ISTANBUL_ZONE_ID))
                        .build());

                cashBoxPort.update(cashBox);


            } else if (useCase.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
                storeCollection.setPos(storeCollection.getPos().subtract(useCase.getAmount()));
                storePaymentTransactionPort.create(StorePaymentTransaction.builder()
                        .storeId(useCase.getStoreId())
                        .pos(useCase.getAmount())
                        .clientId(useCase.getClientId())
                        .eventType(StoreEventType.ADMIN_GET_PAID)
                        .date(ZonedDateTime.now().withZoneSameInstant(ISTANBUL_ZONE_ID))
                        .build());

            }
        }


        storeCollectionPort.update(storeCollection);

        return billingPayment;
    }
}
