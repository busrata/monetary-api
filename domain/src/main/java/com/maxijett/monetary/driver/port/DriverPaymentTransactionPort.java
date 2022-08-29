package com.maxijett.monetary.driver.port;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;

import java.time.ZonedDateTime;
import java.util.List;

public interface DriverPaymentTransactionPort {

    Long createTransaction(DriverPaymentTransaction driverTransaction);

    List<DriverPaymentTransaction> retrieveTransactions(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime, List<DriverEventType> driverEventTypes);
}
