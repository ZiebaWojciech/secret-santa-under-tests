package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
class ExternalUsersSourceConfiguration {

    @Bean
    @Primary
    ParticipantsRepository participantsRepository() {
        return new UsersProviderParticipantsRepository();
    }

    @Bean
    GiftService giftService(ParticipantsRepository participantsRepository) {
        return new GiftService(participantsRepository);
    }


}
