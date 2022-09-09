package com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CommissionConstantCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CommissionConstantCashRepository extends JpaRepository<CommissionConstantCashEntity, Long> {

    @Query("select ccc from CommissionConstantCashEntity ccc where ccc.clientId =:clientId and (ccc.startTime <=:endDate and ccc.endTime >=:startDate)")
    List<CommissionConstantCashEntity> findByDateBetweenAndClientId(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("clientId") Long clientId);

}