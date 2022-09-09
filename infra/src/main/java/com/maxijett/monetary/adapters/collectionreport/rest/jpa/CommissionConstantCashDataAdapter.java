package com.maxijett.monetary.adapters.collectionreport.rest.jpa;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CommissionConstantCashEntity;
import com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository.CommissionConstantCashRepository;
import com.maxijett.monetary.collectionreport.model.CommissionConstantCash;
import com.maxijett.monetary.collectionreport.port.CommissionConstantCashPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionConstantCashDataAdapter implements CommissionConstantCashPort {

    private final CommissionConstantCashRepository commissionConstantCashRepository;

    @Override
    public List<CommissionConstantCash> getCommissionConstantCashListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId){
        return commissionConstantCashRepository.findByDateBetweenAndClientId(startDate, endDate, clientId).stream().map(
                CommissionConstantCashEntity::toModel).collect(Collectors.toList());
    }

}
