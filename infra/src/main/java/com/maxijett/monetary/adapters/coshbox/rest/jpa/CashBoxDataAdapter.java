package com.maxijett.monetary.adapters.coshbox.rest.jpa;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.CashBoxEntity;
import com.maxijett.monetary.adapters.coshbox.rest.jpa.repository.CashBoxRepository;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashBoxDataAdapter implements CashBoxPort {

    private final CashBoxRepository cashBoxRepository;

    @Override
    public CashBox retrieve(Long groupId) {
        return cashBoxRepository.findByGroupId(groupId).toModel();
    }

    @Override
    public CashBox update(CashBox cashBox) {
        return cashBoxRepository.save(fromModel(cashBox)).toModel();
    }

    private CashBoxEntity fromModel(CashBox cashBox) {
        var entity = new CashBoxEntity();
        entity.setId(cashBox.getId());
        entity.setClientId(cashBox.getClientId());
        entity.setGroupId(cashBox.getGroupId());
        entity.setUserId(cashBox.getUserId());
        entity.setCash(cashBox.getCash());
        return entity;
    }
}
