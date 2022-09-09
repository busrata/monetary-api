package com.maxijett.monetary.collectionreport.port;

import com.maxijett.monetary.collectionreport.model.CommissionConstantCash;

import java.time.ZonedDateTime;
import java.util.List;

public interface CommissionConstantCashPort {

    List<CommissionConstantCash> getCommissionConstantCashListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId);

}
