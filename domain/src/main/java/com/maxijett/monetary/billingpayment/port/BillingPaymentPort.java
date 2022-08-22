package com.maxijett.monetary.billingpayment.port;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;

public interface BillingPaymentPort {

    BillingPayment create(BillingPaymentCreate useCase);
}
