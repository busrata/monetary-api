package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.collectionreport.model.*;
import com.maxijett.monetary.collectionreport.model.enumerations.WarmthType;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@Sql(scripts = "classpath:sql/collection-report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionReportControllerTest extends AbstractIT {


    @Test
    public void getDailyBonus() {

        //Given
        LocalDate dateTime = LocalDate.now();

        Long driverId = 1L;

        Boolean isRequestFromMobil = true;

        //When
        ResponseEntity<DriverDailyBonus> response = testRestTemplate.exchange("/api/v1/collection-report/daily-bonus/" + driverId + "?startDate=" + dateTime + "&endDate=" + dateTime + "&isRequestFromMobil=" + isRequestFromMobil,
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<DriverDailyBonus>() {
                });

        //Then
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void retrieveCollectionReportDateRangeByStore() {

        //Given
        Long storeId = 20L;
        LocalDate startDate = LocalDate.of(2022, 4, 1);
        LocalDate endDate = LocalDate.of(2022, 4, 18);

        createCollectionReportRecord(12345L, ZonedDateTime.parse("2022-04-01T08:47:00.000Z"), storeId, "1000", BigDecimal.valueOf(120), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.ZERO, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);
        createCollectionReportRecord(12345L, ZonedDateTime.parse("2022-04-18T08:47:00.000Z"), storeId, "1001", BigDecimal.valueOf(130), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1055, BigDecimal.ZERO, 201L, WarmthType.COLD);

        createCollectionReportRecord(12345L, ZonedDateTime.parse("2022-04-18T08:47:00.000Z"), 30L, "1002", BigDecimal.valueOf(130), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);
        createCollectionReportRecord(12345L, ZonedDateTime.parse("2022-04-19T08:47:00.000Z"), storeId, "1003", BigDecimal.valueOf(130), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);
        createCollectionReportRecord(12345L, ZonedDateTime.parse("2022-04-20T08:47:00.000Z"), storeId, "1004", BigDecimal.valueOf(130), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);

        //When
        ResponseEntity<List<CollectionReport>> response = testRestTemplate.exchange("/api/v1/collection-report/{storeId}/date-range?startDate={startDate}&endDate={endDate}",
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<>() {
                }, storeId, startDate, endDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).isNotNull().hasSize(2)
                .extracting("storeId", "clientId", "orderNumber", "cash", "cashCommission", "driverId", "pos", "distanceFee", "deliveryDistance", "posCommission", "groupId", "warmthType")
                .containsExactlyInAnyOrder(
                        tuple(storeId, 12345L, "1000", new BigDecimal("120.00"), new BigDecimal("52.00"), 315L, new BigDecimal("10.00"), new BigDecimal("0.00"), 1045, new BigDecimal("0.00"), 201L, WarmthType.HOT),
                        tuple(storeId, 12345L, "1001", new BigDecimal("130.00"), new BigDecimal("52.00"), 315L, new BigDecimal("10.00"), new BigDecimal("10.00"), 1055, new BigDecimal("0.00"), 201L, WarmthType.COLD)
                );
    }

    @Test
    public void retrieveCollectionReportAccrualValueByDateBetweenAndStore(){

        //Given
        Long clientId = 20000L;
        BigDecimal ratePos = BigDecimal.valueOf(0.51);
        BigDecimal rateCash = BigDecimal.valueOf(0.96);
        LocalDate startDate = LocalDate.of(2022, 4, 1);
        LocalDate endDate = LocalDate.of(2022, 5, 1);

        createCollectionReportCommissionConstantCashRecord(rateCash, ZonedDateTime.parse("2022-03-20T00:00:00.000Z"), ZonedDateTime.parse("2022-04-10T23:59:59.999Z"), clientId); //+
        createCollectionReportCommissionConstantCashRecord(BigDecimal.valueOf(0.65), ZonedDateTime.parse("2022-04-11T00:00:00.000Z"), ZonedDateTime.parse("2022-04-16T23:59:59.999Z"), clientId); //+
        createCollectionReportCommissionConstantCashRecord(rateCash, ZonedDateTime.parse("2022-05-01T00:00:00.000Z"), ZonedDateTime.parse("2022-05-01T23:59:59.999Z"), clientId); //+
        createCollectionReportCommissionConstantCashRecord(rateCash, ZonedDateTime.parse("2022-05-02T05:00:00.000Z"), ZonedDateTime.parse("2500-01-01T00:00:00.000Z"), clientId); //-

        createCollectionReportCommissionConstantPosRecord(ratePos, ZonedDateTime.parse("2022-04-11T08:00:00.000Z"), ZonedDateTime.parse("2022-04-16T08:00:00.000Z"), clientId); //+
        createCollectionReportCommissionConstantPosRecord(BigDecimal.valueOf(0.55), ZonedDateTime.parse("2022-04-16T08:00:00.000Z"), ZonedDateTime.parse("2022-04-30T08:00:00.000Z"), clientId); //+
        createCollectionReportCommissionConstantPosRecord(ratePos, ZonedDateTime.parse("2022-04-30T08:00:00.000Z"), ZonedDateTime.parse("2022-05-15T08:00:00.000Z"), 20001L); //-

        //When
        ResponseEntity<CommissionConstantAccrualValue> response = testRestTemplate.exchange("/api/v1/collection-report/accrual-value/{clientId}/date-range?startDate={startDate}&endDate={endDate}",
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<>() {
                }, clientId, startDate, endDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CommissionConstantPos> commissionConstantPosList = response.getBody().getCommissionConstantPosList();
        List<CommissionConstantCash> commissionConstantCashList = response.getBody().getCommissionConstantCashList();

        assertThat(commissionConstantPosList).isNotNull().hasSize(2)
                .extracting("rate", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(ratePos, clientId),
                        tuple(BigDecimal.valueOf(0.55), clientId)
                );

        assertThat(commissionConstantPosList.get(0).getEndTime().toLocalDate()).isBetween(startDate, endDate);
        assertThat(commissionConstantPosList.get(1).getEndTime().toLocalDate()).isBetween(startDate, endDate);

        assertThat(response.getBody().getCommissionConstantCashList()).isNotNull().hasSize(3)
                .extracting("rate", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(rateCash, clientId),
                        tuple(rateCash, clientId),
                        tuple(BigDecimal.valueOf(0.65), clientId)
                );
        assertThat(commissionConstantCashList.get(0).getEndTime().toLocalDate()).isBetween(startDate, endDate);
        assertThat(commissionConstantCashList.get(1).getEndTime().toLocalDate()).isBetween(startDate, endDate);
        assertThat(commissionConstantCashList.get(2).getEndTime().toLocalDate()).isBetween(startDate, endDate);
    }

    @Test
    public void retrieveCollectionReportMonthlyByStore() {
        //Given
        Long storeId = 37L;
        LocalDate requestDate = LocalDate.of(2022, 2, 1);

        createCollectionReportRecord(20000L, ZonedDateTime.parse("2022-02-19T08:47:00.000Z"), storeId, "1003", BigDecimal.valueOf(110), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);
        createCollectionReportRecord(20000L, ZonedDateTime.parse("2022-02-20T08:47:00.000Z"), storeId, "1004", BigDecimal.valueOf(120), BigDecimal.valueOf(52), 315L, BigDecimal.ZERO, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);

        createCollectionReportRecord(20000L, ZonedDateTime.parse("2022-02-19T08:47:00.000Z"), 67L, "1005", BigDecimal.valueOf(110), BigDecimal.valueOf(52), 315L, BigDecimal.TEN, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);
        createCollectionReportRecord(20000L, ZonedDateTime.parse("2022-03-20T08:47:00.000Z"), storeId, "1006", BigDecimal.valueOf(120), BigDecimal.valueOf(52), 315L, BigDecimal.ZERO, BigDecimal.TEN, 1045, BigDecimal.ZERO, 201L, WarmthType.HOT);


        //When
        ResponseEntity<List<CollectionReport>> response =
                testRestTemplate.exchange("/api/v1/collection-report/by-store/{storeId}/monthly?requestDate={requestDate}",
                        HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<>() {
                        }, storeId, requestDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).isNotNull().hasSize(2)
                .extracting("storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(storeId, new BigDecimal("110.00"), new BigDecimal("10.00"), 20000L),
                        tuple(storeId, new BigDecimal("120.00"), new BigDecimal("0.00"), 20000L)
                );

    }

}
