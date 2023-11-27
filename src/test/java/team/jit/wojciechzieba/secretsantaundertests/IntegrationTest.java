package team.jit.wojciechzieba.secretsantaundertests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static java.lang.StringTemplate.STR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    MongoDBContainer mongoDBContainer;

    @Test
    @DisplayName("Should create new Participant, when the creation request is valid")
    void shouldCreateNewParticipant_whenValidRequest() {
        // given

        mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
        mongoDBContainer.addExposedPort(20737);
        mongoDBContainer.start();

        ParticipantDto participantDto = new ParticipantDto(
                null,
                "Wojtek",
                "imprezy integracyjne",
                "zielony",
                false
        );

        // when
        webTestClient.post()
                .uri("/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(STR."""
                            {
                                "name": "\{participantDto.name()}",
                                "hobby": "\{participantDto.hobby()}",
                                "favouriteColour": "\{participantDto.favouriteColour()}"
                            }
                        """)
                .exchange()
                .expectStatus()
                .isOk();

    }
}
