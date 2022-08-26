package com.maxijett.monetary.common.usecase;

import com.maxijett.monetary.orderpayment.useCase.OrderPayment;


public class FakeAddPaymentToDriverAndStoreUseCaseHandler implements UseCaseHandler<Boolean, OrderPayment> {

  @Override
  public Boolean handle(OrderPayment useCase) {
    return true;
  }
}
