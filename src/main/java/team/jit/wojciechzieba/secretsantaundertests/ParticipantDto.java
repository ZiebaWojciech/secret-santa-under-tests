package team.jit.wojciechzieba.secretsantaundertests;

import lombok.Data;

public record ParticipantDto(String id, String name, String hobby, String favouriteColour, boolean gifted) {
    static ParticipantDto from(Participant participant) {
        return new ParticipantDto(
                participant.id,
                participant.name,
                participant.hobby,
                participant.favouriteColour,
                participant.gifted
        );
    }

    Participant toDomain() {
        return new Participant(
                null,
                name,
                hobby,
                favouriteColour,
                gifted,
                null
        );
    }
}
