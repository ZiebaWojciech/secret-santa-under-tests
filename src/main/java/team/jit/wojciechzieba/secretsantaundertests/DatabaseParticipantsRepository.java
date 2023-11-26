package team.jit.wojciechzieba.secretsantaundertests;

import java.util.List;

class DatabaseParticipantsRepository implements ParticipantsRepository {

    public DatabaseParticipantsRepository(final SpringJpaParticipantRepository springJpaParticipantRepository) {
        this.springJpaParticipantRepository = springJpaParticipantRepository;
    }

    private final SpringJpaParticipantRepository springJpaParticipantRepository;

    @Override
    public List<Participant> getAll() {
        return springJpaParticipantRepository.findAll();
    }

    @Override
    public String save(Participant participant) {
        return springJpaParticipantRepository.save(participant).id;
    }
}
