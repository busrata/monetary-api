package com.maxijett.monetary.cashbox;

import com.maxijett.monetary.cashbox.model.CashBox;
import com.maxijett.monetary.cashbox.port.CashBoxPort;
import com.maxijett.monetary.cashbox.usecase.CashBoxAmountGet;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class GetAmountFromCashBoxByClientIdOrGroupIdUseCaseHandler implements UseCaseHandler<CashBox, CashBoxAmountGet> {
    private final CashBoxPort cashBoxPort;

    @Override
    public CashBox handle(CashBoxAmountGet cashBoxAmountGet){

        CashBox cashBox = null;
        if(cashBoxAmountGet.getGroupId() != null) {
            cashBox = cashBoxPort.retrieve(cashBoxAmountGet.getGroupId());

        }else if(cashBoxAmountGet.getClientId() != null) {
            List<CashBox> cashBoxList = cashBoxPort.getListByClientId(cashBoxAmountGet.getClientId());
            BigDecimal amount = cashBoxList.stream().map(box -> box.getCash()).reduce(BigDecimal.ZERO, BigDecimal::add);
            cashBox = CashBox.builder()
                    .cash(amount)
                    .clientId(cashBoxAmountGet.getClientId())
                    .build();
        }else{
            throw new NullPointerException("ClientId and GroupId null");
        }

        return cashBox;
    }
}
