package com.maxijett.monetary.billingpayment.adapters;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BillingPaymentPortFakeDataAdapter implements BillingPaymentPort {
    public List<BillingPayment> billings = new ArrayList<>();

    @Override
    public BillingPayment create(BillingPaymentCreate useCase) {
        billings.add(BillingPayment.builder()
                .storeId(useCase.getStoreId())
                .clientId(useCase.getClientId())
                .amount(useCase.getAmount())
                .payloadType(useCase.getPayloadType())
                .payingAccount(useCase.getPayingAccount())
                .paymentType(useCase.getPaymentType())
                .isDeleted(false)
                .build());
        return billings.get(0);
    }

    @Override
    public BillingPayment create(BillingPaymentPrePaidCreate useCase) {
        billings.add(BillingPayment.builder()
                .storeId(useCase.getStoreId())
                .clientId(useCase.getClientId())
                .amount(useCase.getPrePaidBillingAmount())
                .payloadType(useCase.getPayloadType())
                .paymentType(useCase.getPaymentType())
                .build());
        return billings.get(0);
    }

    @Override
    public BillingPayment retrieve(Long id) {
       return BillingPayment.builder()
            .id(3L)
            .isDeleted(false)
            .paymentType(PaymentType.CASH)
            .storeId(30L)
            .amount(BigDecimal.TEN)
            .clientId(20L)
            .payingAccount("storeChainAdmin")
            .build();
    }

    @Override
    public BillingPayment update(Long id) {
        BillingPayment billingPayment = retrieve(id);
        billingPayment.setIsDeleted(true);
        billings.add(retrieve(id));
        return billingPayment;
    }


    public void assertContains(BillingPayment billingPayment) {
        assertThat(billings).containsAnyElementsOf(List.of(billingPayment));
    }


}
