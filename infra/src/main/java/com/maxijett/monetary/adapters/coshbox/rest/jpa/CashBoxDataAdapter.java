package com.maxijett.monetary.adapters.coshbox.rest.jpa;

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
    public CashBox retrive(Long groupId) {
        return cashBoxRepository.findByGroupId(groupId).toModel();
    }
}
