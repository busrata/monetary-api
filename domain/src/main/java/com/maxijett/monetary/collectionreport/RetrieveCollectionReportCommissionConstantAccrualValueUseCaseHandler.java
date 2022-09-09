package com.maxijett.monetary.collectionreport;

import com.maxijett.monetary.collectionreport.model.CommissionConstantAccrualValue;
import com.maxijett.monetary.collectionreport.model.CommissionConstantCash;
import com.maxijett.monetary.collectionreport.model.CommissionConstantPos;
import com.maxijett.monetary.collectionreport.model.ShiftTime;
import com.maxijett.monetary.collectionreport.port.CommissionConstantCashPort;
import com.maxijett.monetary.collectionreport.port.CommissionConstantPosPort;
import com.maxijett.monetary.collectionreport.port.ShiftTimePort;
import com.maxijett.monetary.collectionreport.useCase.CommissionConstantByDateBetweenAndClientIdRetrieve;
import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.util.MonetaryDate;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RetrieveCollectionReportCommissionConstantAccrualValueUseCaseHandler implements UseCaseHandler<CommissionConstantAccrualValue, CommissionConstantByDateBetweenAndClientIdRetrieve> {
    private final CommissionConstantCashPort commissionConstantCashPort;
    private final CommissionConstantPosPort commissionConstantPosPort;
    private final ShiftTimePort shiftTimePort;

    @Override
    public CommissionConstantAccrualValue handle(CommissionConstantByDateBetweenAndClientIdRetrieve useCase){

        ShiftTime shiftTime = shiftTimePort.getShiftTime();

        ZonedDateTime dateRangeFrom = MonetaryDate.convertStartZonedDateTime(useCase.getStartDate(), shiftTime.getNightShiftEndHour());
        ZonedDateTime dateRangeTo = MonetaryDate.convertEndZonedDateTime(useCase.getEndDate(), shiftTime.getNightShiftEndHour());

        List<CommissionConstantCash> commissionConstantCashList = commissionConstantCashPort.getCommissionConstantCashListByDateBetween(dateRangeFrom, dateRangeTo, useCase.getClientId());
        List<CommissionConstantPos> commissionConstantPosList = commissionConstantPosPort.getCommissionConstantPosListByDateBetween(dateRangeFrom, dateRangeTo, useCase.getClientId());

        return new CommissionConstantAccrualValue(commissionConstantCashList, commissionConstantPosList);
    }
}
