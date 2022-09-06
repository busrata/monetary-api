package com.maxijett.monetary.driver.port;

import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import java.util.List;

public interface DriverCashPort {
    DriverCash retrieve(Long driverId, Long groupId);

    DriverCash update(DriverCash driverCash, DriverPaymentTransaction driverPaymentTransaction);

    List<DriverCash> getListByGroupIdGreaterThanZero( Long groupId);

    List<DriverCash> getListByClientIdGreaterThanZero(Long clientId);
}
