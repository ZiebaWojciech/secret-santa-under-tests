package team.jit.wojciechzieba.secretsantaundertests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ContextConfiguration(classes = ExternalUsersSourceConfiguration.class)
@WireMockTest
class ExternalUsersProviderTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldRegisterGift_whenExternalUserProviderUsed() throws JsonProcessingException {
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
                        .withBody(UsersTestFactory.USERS_JSON)
        ));

        // when
        webTestClient.post()
                .uri("/gifts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(giftRegistrationCommand))
                .exchange()
                .expectStatus()
                .isOk();
    }
}