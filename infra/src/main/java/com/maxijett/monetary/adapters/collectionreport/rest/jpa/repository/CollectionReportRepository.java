package com.maxijett.monetary.adapters.collectionreport.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionreport.rest.jpa.entity.CollectionReportEntity;
import com.maxijett.monetary.collectionreport.model.CollectionReport;
import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionReportRepository extends JpaRepository<CollectionReportEntity, Long> {

  @Query("select cr from CollectionReportEntity cr where cr.warmthType =:warmthType and cr.paymentDate >=:startDate and cr.paymentDate<=:endDate and  cr.driverId = :driverId and"
      + "(((EXTRACT(HOUR FROM  cr.paymentDate ) < :startHour) or (EXTRACT(HOUR FROM cr.paymentDate) =:startHour and EXTRACT(MINUTE FROM cr.paymentDate) < :minute)) and (EXTRACT(HOUR FROM  cr.paymentDate ) > :endHour))")
  List<CollectionReportEntity> findByDriverIdAndPaymentDateBetweenAndWarmthTypeAndNoonShift(@Param("driverId") Long driverId,@Param("warmthType") WarmthType warmthType,@Param("startDate")ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate,@Param("startHour") int startHour, @Param("minute") int minute, @Param("endHour") int endHour);

  @Query("select cr from CollectionReportEntity cr where cr.paymentDate >=:startDate and cr.paymentDate <=:endDate and cr.driverId=:driverId and  ((EXTRACT(HOUR FROM cr.paymentDate) =:startHour and EXTRACT(MINUTE FROM cr.paymentDate) >=:minute ) or (EXTRACT(HOUR FROM  cr.paymentDate ) > :startHour) or (EXTRACT(HOUR FROM  cr.paymentDate ) <= :endHour))")
  List<CollectionReportEntity> findByDriverIdAndPaymentDateBetweenNightShift(@Param("driverId")Long driverId, @Param("startDate")ZonedDateTime startDate, @Param("endDate")ZonedDateTime endDate,@Param("startHour") int startHour, @Param("minute") int minute, @Param("endHour") int endHour);
}
