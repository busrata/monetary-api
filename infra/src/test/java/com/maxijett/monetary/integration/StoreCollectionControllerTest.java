package com.maxijett.monetary.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.Assertions.tuple;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StoreCollectionControllerTest extends AbstractIT {

    @Test
    public void getStoreCollectionByClientId() {
        //Given
        Long clientId = 20000L;

        createStoreCollectionRecord(200L, 20L, BigDecimal.valueOf(0), BigDecimal.valueOf(140),
                TariffType.TAXIMETER_HOT, 20000L);
        createStoreCollectionRecord(300L, 20L, BigDecimal.valueOf(60), BigDecimal.valueOf(80),
                TariffType.TAXIMETER_HOT, 20000L);
        createStoreCollectionRecord(400L, 20L, BigDecimal.valueOf(90), BigDecimal.valueOf(100),
                TariffType.TAXIMETER_HOT, 20000L);

        //When
        ResponseEntity<List<StoreCollection>> response = testRestTemplate.exchange(
                "/api/v1/store-collection?clientId=" + clientId,
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<List<StoreCollection>>() {
                });

        //Then
        List<StoreCollection> responseList = response.getBody();

        assertThat(responseList).isNotNull().hasSize(3)
                .extracting("storeId", "groupId", "cash", "pos", "tariffType", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(200L, 20L, new BigDecimal("0.00"), new BigDecimal("140.00"),
                                TariffType.TAXIMETER_HOT,
                                20000L),
                        tuple(300L, 20L, new BigDecimal("60.00"), new BigDecimal("80.00"),
                                TariffType.TAXIMETER_HOT,
                                20000L),
                        tuple(400L, 20L, new BigDecimal("90.00"), new BigDecimal("100.00"),
                                TariffType.TAXIMETER_HOT,
                                20000L)
                );
    }

    @Test
    public void getStoreCollectionByGroupId() {
        //Given
        Long groupId = 30L;

        createStoreCollectionRecord(200L, 30L, BigDecimal.valueOf(0), BigDecimal.valueOf(140),
                TariffType.TAXIMETER_HOT, 30000L);
        createStoreCollectionRecord(300L, 30L, BigDecimal.valueOf(60), BigDecimal.valueOf(80),
                TariffType.TAXIMETER_HOT, 30000L);
        createStoreCollectionRecord(400L, 30L, BigDecimal.valueOf(90), BigDecimal.valueOf(100),
                TariffType.TAXIMETER_HOT, 30000L);

        //When
        ResponseEntity<List<StoreCollection>> response = testRestTemplate.exchange(
                "/api/v1/store-collection?groupId=" + groupId,
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<List<StoreCollection>>() {
                });

        //Then
        List<StoreCollection> responseList = response.getBody();

        assertThat(responseList).isNotNull().hasSize(3)
                .extracting("storeId", "groupId", "cash", "pos", "tariffType", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(200L, 30L, new BigDecimal("0.00"), new BigDecimal("140.00"),
                                TariffType.TAXIMETER_HOT,
                                30000L),
                        tuple(300L, 30L, new BigDecimal("60.00"), new BigDecimal("80.00"),
                                TariffType.TAXIMETER_HOT,
                                30000L),
                        tuple(400L, 30L, new BigDecimal("90.00"), new BigDecimal("100.00"),
                                TariffType.TAXIMETER_HOT,
                                30000L)
                );
    }

    @Test
    public void getStoreCollectionWithNullVariables() throws Exception {
        //When
        var response = testRestTemplate.exchange(
                "/api/v1/store-collection",
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<>() {
                });

        //Then
        AssertionsForClassTypes.assertThat(response).isNotNull()
                .returns(HttpStatus.UNPROCESSABLE_ENTITY, from(ResponseEntity::getStatusCode));

    }

}
