package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
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
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@DomainComponent
@RequiredArgsConstructor
public class GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler implements
        UseCaseHandler<BillingPayment, BillingPaymentCreate> {

    private final CashBoxPort cashBoxPort;

    private final BillingPaymentPort billingPaymentPort;

    private final StoreCollectionPort storeCollectionPort;

    @Override
    public BillingPayment handle(BillingPaymentCreate useCase) {

        BillingPayment billingPayment = billingPaymentPort.create(useCase);
        StoreCollection storeCollection = storeCollectionPort.retrieve(useCase.getStoreId());

        if (useCase.getPayloadType().equals(PayloadType.COLLECTION)) {
            if (useCase.getCash().compareTo(BigDecimal.ZERO) > 0) {
                CashBox cashBox = cashBoxPort.retrieve(storeCollection.getGroupId());
                cashBox.setCash(cashBox.getCash().subtract(useCase.getCash()));

                storeCollection.setCash(storeCollection.getCash().subtract(useCase.getCash()));

                cashBoxPort.update(cashBox, CashBoxTransaction.builder()
                        .dateTime(ZonedDateTime.now(ZoneOffset.UTC))
                        .cashBoxEventType(CashBoxEventType.ADMIN_PAY)
                        .payingAccount(useCase.getPayingAccount())
                        .amount(useCase.getCash())
                        .build());


            } else if (useCase.getPos().compareTo(BigDecimal.ZERO) > 0) {
                storeCollection.setPos(storeCollection.getPos().subtract(useCase.getPos()));
            }
        }


        storeCollectionPort.update(storeCollection, StorePaymentTransaction.builder()
                .storeId(useCase.getStoreId())
                .createOn(ZonedDateTime.now(ZoneOffset.UTC))
                .pos(useCase.getPos())
                .cash(useCase.getCash())
                .eventType(StoreEventType.ADMIN_GET_PAID)
                .clientId(useCase.getClientId())
                .build());

        return billingPayment;
    }
}
