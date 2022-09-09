package com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CommissionConstantPosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CommissionConstantPosRepository extends JpaRepository<CommissionConstantPosEntity, Long> {

    @Query("select ccc from CommissionConstantPosEntity ccc where ccc.clientId =:clientId and (ccc.startTime <=:endDate and ccc.endTime >=:startDate)")
    List<CommissionConstantPosEntity> findByDateBetweenAndClientId(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("clientId") Long clientId);
}
