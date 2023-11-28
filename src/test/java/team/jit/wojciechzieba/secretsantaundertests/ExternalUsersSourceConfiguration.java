package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
class ExternalUsersSourceConfiguration {

    @Bean
    @Primary
    ParticipantsRepository participantsRepository(WebClient webClient) {
        return new UsersProviderParticipantsRepository(webClient);
    }

    @Bean
    WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    GiftService giftService(ParticipantsRepository participantsRepository) {
        return new GiftService(participantsRepository);
    }


}
