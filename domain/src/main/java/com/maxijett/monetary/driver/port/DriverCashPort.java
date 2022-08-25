package com.maxijett.monetary.driver.port;

import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;

public interface DriverCashPort {
    DriverCash retrieve(Long driverId, Long groupId);

    DriverCash update(DriverCash driverCash, DriverPaymentTransaction driverPaymentTransaction);
}
