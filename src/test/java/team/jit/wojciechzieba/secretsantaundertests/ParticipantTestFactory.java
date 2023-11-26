package team.jit.wojciechzieba.secretsantaundertests;

import java.util.UUID;


class ParticipantTestFactory {

    static class ParticipantBuilder {
        private String name;
        private String hobby;
        private String favouriteColour;

        static ParticipantBuilder participant() {
            return new ParticipantBuilder();
        }

        ParticipantBuilder withName(String name) {
            this.name = name;
            return this;
        }

        ParticipantBuilder withHobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        ParticipantBuilder withFavouriteColour(String favouriteColour) {
            this.favouriteColour = favouriteColour;
            return this;
        }

        Participant build() {
            return new Participant(
                    UUID.randomUUID().toString(),
                    this.name,
                    this.hobby,
                    this.favouriteColour,
                    false,
                    0
            );
        }
    }
}
