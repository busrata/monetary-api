package com.maxijett.monetary.adapters.cashbox.rest.jpa;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.CashBoxTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.CashBoxTransactionRepository;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashBoxTransactionDataAdapter implements CashBoxTransactionPort {

    private final CashBoxTransactionRepository cashBoxTransactionRepository;

    @Override
    public Long createTransaction(CashBoxTransaction cashBoxTransaction) {
        CashBoxTransactionEntity entity = new CashBoxTransactionEntity();
        entity.setDriverId(cashBoxTransaction.getDriverId());
        entity.setAmount(cashBoxTransaction.getAmount());
        entity.setDateTime(cashBoxTransaction.getDateTime());
        entity.setPayingAccount(cashBoxTransaction.getPayingAccount());
        entity.setEventType(cashBoxTransaction.getCashBoxEventType());

        return cashBoxTransactionRepository.save(entity).getId();

    }
}
