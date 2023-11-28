package team.jit.wojciechzieba.secretsantaundertests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ContextConfiguration(classes = ExternalUsersSourceConfiguration.class )
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