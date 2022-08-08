package com.maxijett.monetary.cashbox.port;

import com.maxijett.monetary.cashbox.model.CashBox;

public interface CashBoxPort {
    CashBox retrive(Long groupId);
}
