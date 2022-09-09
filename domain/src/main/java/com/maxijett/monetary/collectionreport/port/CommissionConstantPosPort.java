package com.maxijett.monetary.collectionreport.port;

import com.maxijett.monetary.collectionreport.model.CommissionConstantPos;

import java.time.ZonedDateTime;
import java.util.List;

public interface CommissionConstantPosPort {

    List<CommissionConstantPos> getCommissionConstantPosListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId);

}
