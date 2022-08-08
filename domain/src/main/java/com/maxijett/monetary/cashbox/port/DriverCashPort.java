package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.DriverCash;

public interface DriverCashPort {
    DriverCash retrieve(Long driverId);
}
