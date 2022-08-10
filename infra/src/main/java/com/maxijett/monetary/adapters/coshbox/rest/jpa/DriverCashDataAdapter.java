package com.maxijett.monetary.adapters.coshbox.rest.jpa;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.repository.DriverCashRepository;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.port.DriverCashPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverCashDataAdapter implements DriverCashPort {

    private final DriverCashRepository driverCashRepository;

    @Override
    public DriverCash retrieve(Long driverId) {
        return driverCashRepository.findByDriverId(driverId).toModel();
    }
}
