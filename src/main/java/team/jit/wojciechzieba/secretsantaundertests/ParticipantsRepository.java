package team.jit.wojciechzieba.secretsantaundertests;

import java.util.List;

public interface ParticipantsRepository {
    List<Participant> getAll();

    String save(Participant participant);
}
