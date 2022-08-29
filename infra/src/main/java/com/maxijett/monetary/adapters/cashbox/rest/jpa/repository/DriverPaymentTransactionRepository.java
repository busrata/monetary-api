package com.maxijett.monetary.adapters.cashbox.rest.jpa.repository;

import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface DriverPaymentTransactionRepository extends JpaRepository<DriverPaymentTransactionEntity, Long> {

    List<DriverPaymentTransactionEntity> findAllByDriverIdAndGroupIdAndDateTimeAfterAndDateTimeBeforeAndEventTypeIn(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime, List<DriverEventType> eventTypes);

}
