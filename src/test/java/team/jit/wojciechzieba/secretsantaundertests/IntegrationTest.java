package team.jit.wojciechzieba.secretsantaundertests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static java.lang.StringTemplate.STR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
class IntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
            .withExposedPorts();


    @Test
    @DisplayName("Should create new Participant, when the creation request is valid")
    void shouldCreateNewParticipant_whenValidRequest() {
        // given

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
                .bodyValue(STR. """
                            {
                                "name": "\{ participantDto.name() }",
                                "hobby": "\{ participantDto.hobby() }",
                                "favouriteColour": "\{ participantDto.favouriteColour() }"
                            }
                        """ )
                .exchange()
                .expectStatus()
                .isOk();

    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void parametrizedTestContainerTest(String param) {
        log.warn(
                STR. "\{param} test \{ mongoDBContainer.getContainerId() }"
        );
    }
}
