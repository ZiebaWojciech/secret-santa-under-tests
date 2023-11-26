package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfiguration {
    @Bean
    ParticipantsRepository participantsRepository(SpringJpaParticipantRepository springJpaParticipantRepository) {
        return new DatabaseParticipantsRepository(
                springJpaParticipantRepository
        );
    }
}
