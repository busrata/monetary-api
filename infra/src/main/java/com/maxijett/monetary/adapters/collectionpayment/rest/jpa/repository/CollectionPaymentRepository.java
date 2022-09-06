package com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CollectionPaymentRepository extends JpaRepository<CollectionPaymentEntity, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update CollectionPaymentEntity c set c.isDeleted = true where c.id =:id")
    int updateCollectionPaymentIsDeleted(@Param("id") Long id);

    List<CollectionPaymentEntity> findAllByDriverIdAndGroupIdAndCreateOnBetween(Long driverId, Long groupId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);

    @Query("select c from CollectionPaymentEntity c where (c.storeId) =:storeId and year(c.createOn) =:year and month(c.createOn) =:month")
    List<CollectionPaymentEntity> getAllMonthlyByStore(@Param("storeId") Long storeId, @Param("year") int year, @Param("month") int month);

    List<CollectionPaymentEntity> findAllByGroupIdAndCreateOnBetween(Long groupId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);

    List<CollectionPaymentEntity> findAllByStoreIdAndCreateOnBetween(Long storeId, ZonedDateTime firstDate, ZonedDateTime lastDate);
}
