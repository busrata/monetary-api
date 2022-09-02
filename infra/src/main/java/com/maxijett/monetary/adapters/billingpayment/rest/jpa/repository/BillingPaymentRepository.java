package com.maxijett.monetary.adapters.billingpayment.rest.jpa.repository;

import com.maxijett.monetary.adapters.billingpayment.rest.jpa.entity.BillingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BillingPaymentRepository extends JpaRepository<BillingPaymentEntity, Long> {

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("update BillingPaymentEntity b set b.isDeleted = true where b.id =:id")
  int updateBillingPaymentIsDeleted(@Param("id") Long id);

  List<BillingPaymentEntity> findAllByGroupIdAndCreateOnBetween(Long groupId, ZonedDateTime startTime, ZonedDateTime endTime);
}
