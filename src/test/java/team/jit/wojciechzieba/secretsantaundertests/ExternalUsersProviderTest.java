package team.jit.wojciechzieba.secretsantaundertests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ExternalUsersSourceConfiguration.class)
@WireMockTest(httpPort = 9999)
class ExternalUsersProviderTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldRegisterGift_whenExternalUserProviderUsed() {
        // given
        GiftRegistrationCommand giftRegistrationCommand = new GiftRegistrationCommand(
                "Maciej",
                "czarny",
                "dodatkowe zadanie",
                "nadgodziny"
        );

        stubFor(get("/users").willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(UsersTestFactory.USERS_JSON)
        ));

        // when
        ResponseEntity<GiftRegistrationResponse> response = testRestTemplate
                .exchange(
                        "/gifts",
                        HttpMethod.POST,
                        new HttpEntity<>(giftRegistrationCommand),
                        GiftRegistrationResponse.class
                );
        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
    }
}