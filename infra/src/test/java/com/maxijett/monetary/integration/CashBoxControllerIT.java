package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.coshbox.rest.dto.CashBoxAddDTO;
import com.maxijett.monetary.adapters.coshbox.rest.dto.CashBoxDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
public class CashBoxControllerIT extends AbstractIT {
    @Test
    public void addCashToCashBoxByDriver() {

        //Given
        CashBoxAddDTO cashBoxDTO = CashBoxAddDTO.builder()
                .driverId(1L)
                .clientId(20000L)
                .groupId(20L)
                .payingAccount("StoreChainAdmin")
                .prePaidAmount(BigDecimal.ZERO)
                .amount(BigDecimal.valueOf(100))
                .createOn(ZonedDateTime.now()).build();


        //When
        ResponseEntity<CashBoxDTO> response = testRestTemplate.exchange("/api/v1/cashbox", HttpMethod.POST, new HttpEntity<>(cashBoxDTO), new ParameterizedTypeReference<CashBoxDTO>() {});

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
