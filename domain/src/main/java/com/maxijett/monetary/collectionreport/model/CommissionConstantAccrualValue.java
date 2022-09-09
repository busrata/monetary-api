package com.maxijett.monetary.collectionreport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionConstantAccrualValue {

    private List<CommissionConstantCash> commissionConstantCashList;

    private List<CommissionConstantPos> commissionConstantPosList;

}
