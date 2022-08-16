package com.maxijett.monetary.adapters.coshbox.rest.jpa;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.DriverCashEntity;
import com.maxijett.monetary.adapters.coshbox.rest.jpa.repository.DriverCashRepository;
import com.maxijett.monetary.cashbox.model.DriverCash;
import com.maxijett.monetary.cashbox.port.DriverCashPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverCashDataAdapter implements DriverCashPort {

    DriverCashRepository driverCashRepository;

    @Override
    public DriverCash retrieve(Long driverId, Long groupId) {
        return driverCashRepository.findByDispatchDriverIdAndGroupId(driverId, groupId).toModel();
    }

    @Override
    public DriverCash update(DriverCash driverCash) {
        return driverCashRepository.save(fromModel(driverCash)).toModel();
    }

    private DriverCashEntity fromModel(DriverCash driverCash) {
        DriverCashEntity entity = new DriverCashEntity();
        entity.setId(driverCash.getId());
        entity.setClientId(driverCash.getClientId());
        entity.setGroupId(driverCash.getGroupId());
        entity.setCash(driverCash.getCash());
        entity.setDispatchDriverId(driverCash.getDispatchDriverId());
        entity.setPrepaidCollectionCash(driverCash.getPrepaidCollectionCash());
        return entity;
    }
}
