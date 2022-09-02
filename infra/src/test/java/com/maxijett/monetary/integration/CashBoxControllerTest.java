package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxAddDTO;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxDTO;
import com.maxijett.monetary.common.rest.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.from;

@IT
@Sql(scripts = "classpath:sql/cash-box.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CashBoxControllerTest extends AbstractIT {


    @Test
    public void addCashToCashBoxByDriver() {

        //Given
        CashBoxAddDTO cashBoxDTO = CashBoxAddDTO.builder()
                .driverId(1L)
                .clientId(20000L)
                .groupId(20L)
                .payingAccount("cashBox")
                .prePaidAmount(BigDecimal.ZERO)
                .amount(BigDecimal.valueOf(100))
                .createOn(ZonedDateTime.now()).build();


        //When
        var response = testRestTemplate.exchange("/api/v1/cashbox",
                HttpMethod.POST, new HttpEntity<>(cashBoxDTO, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("50.00"), response.getBody().getCash());
        assertEquals(cashBoxDTO.getGroupId(), response.getBody().getGroupId());
    }

    @Test
    public void failAddCashBoxByDriverWhenWrongGroupId() {

        Long wrongGroupId = 987654321L;
        //Given
        CashBoxAddDTO cashBoxDTO = CashBoxAddDTO.builder()
                .driverId(1L)
                .clientId(20000L)
                .groupId(wrongGroupId)
                .payingAccount("cashBox")
                .prePaidAmount(BigDecimal.ZERO)
                .amount(BigDecimal.valueOf(100))
                .createOn(ZonedDateTime.now()).build();


        //When
        var response = testRestTemplate.exchange("/api/v1/cashbox",
                HttpMethod.POST, new HttpEntity<>(cashBoxDTO, null), new ParameterizedTypeReference<ErrorResponse>() {
                });

        //Then
        assertThat(response).isNotNull()
                        .returns(HttpStatus.UNPROCESSABLE_ENTITY, from(ResponseEntity::getStatusCode));

    }

    @Test
    public void addCashToCashBoxByAdmin() {

        //Given
        CashBoxAddDTO cashBoxDTO = CashBoxAddDTO.builder()
                .driverId(1L)
                .clientId(20000L)
                .groupId(20L)
                .payingAccount("StoreChain")
                .prePaidAmount(BigDecimal.ZERO)
                .amount(BigDecimal.valueOf(65))
                .createOn(ZonedDateTime.now()).build();

        //When
        var response = testRestTemplate.exchange("/api/v1/cashbox",
                HttpMethod.POST, new HttpEntity<>(cashBoxDTO, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("15.00"), response.getBody().getCash());
        assertEquals(cashBoxDTO.getGroupId(), response.getBody().getGroupId());

    }

    @Test
    public void getAmountFromCashBoxByGroupId() throws Exception {

        //Given
        Long groupId = 30L;

        //When
        var response = testRestTemplate.exchange("/api/v1/cashbox/amount-by-owner?groupId=" + groupId,
                HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        CashBoxDTO cashBox = response.getBody();
        //Then

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groupId, cashBox.getGroupId());
        assertEquals(new BigDecimal("30.00"), cashBox.getCash());

    }

    @Test
    public void getAmountFromCashBoxByClientId() throws Exception {

        //Given
        Long clientId = 20000L;

        //When
        var response = testRestTemplate.exchange("/api/v1/cashbox/amount-by-owner?clientId=" + clientId,
                HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        CashBoxDTO cashBox = response.getBody();

        //Then
        BigDecimal expectedTotalClientAmount = new BigDecimal("160.00");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientId, cashBox.getClientId());
        assertEquals(expectedTotalClientAmount, cashBox.getCash());

    }

    @Test
    public void getAmountFromCashBoxWithNullVariables() throws Exception {

        String groupId = "";
        String clientId = "";

        //When
        var response = testRestTemplate.exchange(
                "/api/v1/cashbox/amount-by-owner?clientId={clientId}&groupId={groupId}",
                HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<ErrorResponse>() {
                }, clientId, groupId);

        //Then

        assertThat(response).isNotNull()
                .returns(HttpStatus.UNPROCESSABLE_ENTITY, from(ResponseEntity::getStatusCode));
    }
}
