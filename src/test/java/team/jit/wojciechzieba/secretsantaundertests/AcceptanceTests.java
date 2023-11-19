package team.jit.wojciechzieba.secretsantaundertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AcceptanceTests {

    // Should_ExpectedBehaviour_When_StateUnderTest
    // When_StateUnderTest_Expect_ExpectedBehaviour
    // Given_Preconditions_When_StateUnderTestThen_ExpectedBehaviour
    // MethodName_StateUnderTest

    @Test
    void shouldReturnPairForRegisteredEmail(@Autowired WebTestClient client) {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk();
    }
}
