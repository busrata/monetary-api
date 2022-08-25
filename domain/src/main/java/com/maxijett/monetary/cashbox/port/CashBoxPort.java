package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;

public interface CashBoxPort {
    CashBox retrieve(Long groupId);

    CashBox update(CashBox cashBox, CashBoxTransaction cashBoxTransaction);
}
