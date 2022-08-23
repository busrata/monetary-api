package com.maxijett.monetary.billingpayment.adapters;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;

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

    public void assertContains(BillingPayment billingPayment) {
        assertThat(billings).containsAnyElementsOf(List.of(billingPayment));
    }


}
