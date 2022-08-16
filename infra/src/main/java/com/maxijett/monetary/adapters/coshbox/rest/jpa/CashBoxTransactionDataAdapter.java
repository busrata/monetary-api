package com.maxijett.monetary.adapters.coshbox.rest.jpa;

import com.maxijett.monetary.adapters.coshbox.rest.jpa.entity.CashBoxTransactionEntity;
import com.maxijett.monetary.adapters.coshbox.rest.jpa.repository.CashBoxTransactionRepository;
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

        return cashBoxTransactionRepository.save(entity).getId();

    }
}
