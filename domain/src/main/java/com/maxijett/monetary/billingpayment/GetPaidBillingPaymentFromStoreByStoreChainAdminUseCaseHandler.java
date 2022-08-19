package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.common.usecase.UseCaseHandler;

public class GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler implements
    UseCaseHandler<BillingPayment, BillingPaymentCreate> {


  @Override
  public BillingPayment handle(BillingPaymentCreate useCase) {
    return null;
  }
}
