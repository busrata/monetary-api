package com.maxijett.monetary.integration;


import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@IT
public class CollectionPaymentControllerTest extends AbstractIT {

  @Test
  public void saveCollectionPaymentByDriver(){

    //Given
    CollectionPaymentDTO collectionPaymentDTO = CollectionPaymentDTO.builder().build();

    //When
    ResponseEntity<CollectionPayment> response = testRestTemplate.exchange("/api/v1/collection-payment/by-driver",
        HttpMethod.POST, new HttpEntity<>(collectionPaymentDTO, null), new ParameterizedTypeReference<CollectionPayment>() {
        });

    //Then
    CollectionPayment responseDTO = response.getBody();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(collectionPaymentDTO.getCash(), responseDTO.getCash());
    Assertions.assertEquals(collectionPaymentDTO.getDriverId(), responseDTO.getDriverId());
    Assertions.assertEquals(collectionPaymentDTO.getStoreId(), responseDTO.getStoreId());
    Assertions.assertEquals(collectionPaymentDTO.getGroupId(), responseDTO.getGroupId());
    Assertions.assertNotNull(responseDTO.getId());

  }

}
