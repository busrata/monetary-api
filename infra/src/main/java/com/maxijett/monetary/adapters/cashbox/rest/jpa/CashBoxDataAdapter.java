package com.maxijett.monetary.adapters.cashbox.rest.jpa;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.CashBoxEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.CashBoxTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.CashBoxRepository;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.CashBoxTransactionRepository;
import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashBoxDataAdapter implements CashBoxPort {

    private final CashBoxRepository cashBoxRepository;

    private final CashBoxTransactionRepository cashBoxTransactionRepository;

    @Override
    public CashBox retrieve(Long groupId) {
        return cashBoxRepository.findByGroupId(groupId).map(CashBoxEntity::toModel)
                .orElseThrow(() -> new MonetaryApiBusinessException("monetaryapi.cashbox.notFound", String.valueOf(groupId)));
    }

    @Override
    public CashBox update(CashBox cashBox, CashBoxTransaction cashBoxTransaction) {

        cashBoxTransactionRepository.save(fromModel(cashBoxTransaction));

        return cashBoxRepository.save(fromModel(cashBox)).toModel();
    }

    public List<CashBox> getListByClientId(Long clientId) {
        return cashBoxRepository.findAllByClientId(clientId).stream().map(CashBoxEntity::toModel).collect(
                Collectors.toList());
    }

    private CashBoxEntity fromModel(CashBox cashBox) {
        var entity = new CashBoxEntity();
        entity.setId(cashBox.getId());
        entity.setClientId(cashBox.getClientId());
        entity.setGroupId(cashBox.getGroupId());
        entity.setUserId(cashBox.getUserId());
        entity.setCash(cashBox.getCash());
        entity.setRecordType(cashBox.getRecordType());
        return entity;
    }

    private CashBoxTransactionEntity fromModel(CashBoxTransaction cashBoxTransaction) {
        var entity = new CashBoxTransactionEntity();
        entity.setAmount(cashBoxTransaction.getAmount());
        entity.setEventType(cashBoxTransaction.getCashBoxEventType());
        entity.setDateTime(cashBoxTransaction.getDateTime());
        entity.setDriverId(cashBoxTransaction.getDriverId());
        entity.setPayingAccount(cashBoxTransaction.getPayingAccount());

        return entity;
    }
}
