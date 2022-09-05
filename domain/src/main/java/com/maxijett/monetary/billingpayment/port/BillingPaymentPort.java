package com.maxijett.monetary.billingpayment.port;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;

import java.time.ZonedDateTime;
import java.util.List;

public interface BillingPaymentPort {

    BillingPayment create(BillingPaymentCreate useCase);

    BillingPayment retrieve(Long id);

    BillingPayment update(Long id);

    BillingPayment create(BillingPaymentPrePaidCreate useCase);

    List<BillingPayment> getAllByGroupIdAndCreateOn(Long groupId, ZonedDateTime startTime, ZonedDateTime endTime);

    List<BillingPayment> retrieveBillingPaymentMonthlyByStore(Long storeId, ZonedDateTime requestDate);
}
