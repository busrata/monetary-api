package com.maxijett.monetary.driver;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.usecase.DriverCashGet;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetDriverAmountFromDriverCashUseCaseHandler implements
    UseCaseHandler<DriverCash, DriverCashGet> {

  private final DriverCashPort driverCashPort;

  @Override
  public DriverCash handle(DriverCashGet useCase) {

    return driverCashPort.retrieve(useCase.getDriverId(), useCase.getGroupId());
  }
}