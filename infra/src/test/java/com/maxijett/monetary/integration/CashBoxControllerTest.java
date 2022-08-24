package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxAddDTO;
import com.maxijett.monetary.adapters.cashbox.rest.dto.CashBoxDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ResponseEntity<CashBoxDTO> response = testRestTemplate.exchange("/api/v1/cashbox",
                HttpMethod.POST, new HttpEntity<>(cashBoxDTO, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("50.00"), response.getBody().getCash());
        assertEquals(cashBoxDTO.getGroupId(), response.getBody().getGroupId());
    }

    @Test
    public void addCashToCashBoxByAdmin(){

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
        ResponseEntity<CashBoxDTO> response = testRestTemplate.exchange("/api/v1/cashbox",
                HttpMethod.POST, new HttpEntity<>(cashBoxDTO, null), new ParameterizedTypeReference<CashBoxDTO>() {
                });

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("15.00"), response.getBody().getCash());
        assertEquals(cashBoxDTO.getGroupId(), response.getBody().getGroupId());


    }
}
