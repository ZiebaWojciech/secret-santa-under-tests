package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringJpaParticipantRepository extends MongoRepository<Participant, Integer> {
}
