package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class AppConfiguration {
    @Bean
    GiftService giftService(ParticipantsRepository participantsRepository) {
        return new GiftService(participantsRepository);
    }

    @Bean
    ParticipantsRepository participantsRepository(SpringJpaParticipantRepository springJpaParticipantRepository) {
        return new DatabaseParticipantsRepository(
                springJpaParticipantRepository
        );
    }
}
