package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;

import java.util.List;

public interface CashBoxPort {
    CashBox retrieve(Long groupId);

    List<CashBox> getListByClientId(Long clientId);
    CashBox update(CashBox cashBox, CashBoxTransaction cashBoxTransaction);
}
