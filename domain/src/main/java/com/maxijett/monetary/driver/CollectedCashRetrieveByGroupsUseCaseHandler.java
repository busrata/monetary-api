package com.maxijett.monetary.driver;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.port.DriverPaymentTransactionPort;
import com.maxijett.monetary.driver.useCase.CollectedCashRetrieve;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.maxijett.monetary.driver.model.enumeration.DriverEventType.PACKAGE_DELIVERED;
import static com.maxijett.monetary.driver.model.enumeration.DriverEventType.SUPPORT_ACCEPTED;

@DomainComponent
@RequiredArgsConstructor
public class CollectedCashRetrieveByGroupsUseCaseHandler implements UseCaseHandler<List<DriverPaymentTransaction>, CollectedCashRetrieve> {

    private final DriverPaymentTransactionPort driverPaymentTransactionPort;

    @Override
    public List<DriverPaymentTransaction> handle(CollectedCashRetrieve useCase) {
        return driverPaymentTransactionPort.retrieveTransactions(useCase.getDriverId(), useCase.getGroupId(), List.of(PACKAGE_DELIVERED, SUPPORT_ACCEPTED))
                .stream()
                .sorted(Comparator.comparing(DriverPaymentTransaction::getDateTime))
                .collect(Collectors.toList());
    }
}
