package team.jit.wojciechzieba.secretsantaundertests;

import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ExternalUsersSourceConfiguration.class)
class ExternalUsersProviderTestWithTestcontainer {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;


    private GenericContainer<?> createContainer() {
        MountableFile externalService = MountableFile.forHostPath(
                Paths.get("UsersProvider-0.0.1-SNAPSHOT.jar"));
        var container = new GenericContainer<>("openjdk:21-slim")
                .withCopyFileToContainer(externalService, "/tmp/provider.jar")
                .withExposedPorts(8080)
                .withCommand("java", "-jar", "/tmp/provider.jar");

        container.setPortBindings(List.of("9999:8080"));

        return container;
    }

    @Test
    void shouldRegisterGift_whenExternalUserProviderUsed() {
        try (var container = createContainer()) {
            container.start();

            // given
            GiftRegistrationCommand giftRegistrationCommand = new GiftRegistrationCommand(
                    "Maciej",
                    "czarny",
                    "dodatkowe zadanie",
                    "nadgodziny"
            );

            // when
            ResponseEntity<GiftRegistrationResponse> response = testRestTemplate
                    .exchange(
                            "/gifts",
                            HttpMethod.POST,
                            new HttpEntity<>(giftRegistrationCommand),
                            GiftRegistrationResponse.class
                    );

            // then
            Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        }

    }
}