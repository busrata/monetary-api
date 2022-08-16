package com.maxijett.monetary.driver.port;

import com.maxijett.monetary.driver.model.DriverCash;

public interface DriverCashPort {
    DriverCash retrieve(Long driverId, Long groupId);

    DriverCash update(DriverCash driverCash);
}
