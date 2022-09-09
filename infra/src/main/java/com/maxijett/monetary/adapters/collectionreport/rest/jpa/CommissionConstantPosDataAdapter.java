package com.maxijett.monetary.adapters.collectionreport.rest.jpa;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CommissionConstantPosEntity;
import com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository.CommissionConstantPosRepository;
import com.maxijett.monetary.collectionreport.model.CommissionConstantPos;
import com.maxijett.monetary.collectionreport.port.CommissionConstantPosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionConstantPosDataAdapter implements CommissionConstantPosPort {
    private final CommissionConstantPosRepository commissionConstantPosRepository;

    @Override
    public List<CommissionConstantPos> getCommissionConstantPosListByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate, Long clientId){
        return commissionConstantPosRepository.findByDateBetweenAndClientId(startDate, endDate, clientId).stream().map(
                CommissionConstantPosEntity::toModel).collect(Collectors.toList());
    }
}
