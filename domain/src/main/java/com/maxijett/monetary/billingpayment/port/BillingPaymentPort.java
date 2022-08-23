package com.maxijett.monetary.billingpayment.port;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;

public interface BillingPaymentPort {

    BillingPayment create(BillingPaymentCreate useCase);

    BillingPayment retrieve(Long id);

    BillingPayment update(Long id);

    BillingPayment create(BillingPaymentPrePaidCreate useCase);
}
