package com.maxijett.monetary.driver;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.port.DriverCashPort;
import com.maxijett.monetary.driver.useCase.DriverCashListRetrieve;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetDriverCashInstantListGreaterThanZeroUseCaseHandler implements UseCaseHandler<List<DriverCash>, DriverCashListRetrieve> {

  private final DriverCashPort driverCashPort;

  @Override
  public List<DriverCash> handle(DriverCashListRetrieve useCase) {
    List<DriverCash> driverCashList = null;
    if (useCase.getGroupId() != null && useCase.getClientId() == null)
      driverCashList = driverCashPort.getListByGroupIdGreaterThanZero(useCase.getGroupId());
    else if (useCase.getGroupId() == null && useCase.getClientId() != null){
      driverCashList = sumCashByDriverId(driverCashPort.getListByClientIdGreaterThanZero(useCase.getClientId()));
    }

    return driverCashList;
  }

  private List<DriverCash> sumCashByDriverId(List<DriverCash> driverCashList) {
    List<DriverCash> sumList = new ArrayList<>();

    driverCashList.forEach(driverCash -> {
          if (sumList.stream().noneMatch(i -> i.getDriverId() == driverCash.getDriverId())) {
            BigDecimal sum = driverCashList.stream()
                .filter(i -> i.getDriverId() == driverCash.getDriverId()).map(DriverCash::getCash)
                .reduce(BigDecimal::add).get();

            driverCash.setCash(sum);
            sumList.add(driverCash);
          }
        }
    );
    return sumList;
  }
}

