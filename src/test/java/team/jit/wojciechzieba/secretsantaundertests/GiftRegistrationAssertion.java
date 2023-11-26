package team.jit.wojciechzieba.secretsantaundertests;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor(staticName = "assertThatGiftRegistrationResponse")
class GiftRegistrationAssertion {
    private final GiftRegistrationResponse giftRegistrationResponse;

    public GiftRegistrationAssertion foundParticipantWithName(String name) {
        Assertions.assertThat(giftRegistrationResponse.name()).isEqualTo(name);
        return this;
    }

    public GiftRegistrationAssertion didNotMatchWithAnything() {
        Assertions.assertThat(giftRegistrationResponse.matchingLevel()).isEqualTo(MatchingLevel.NONE);
        return this;
    }

    public GiftRegistrationAssertion wasPickedBySimilarHobby() {
        Assertions.assertThat(giftRegistrationResponse.matchingLevel()).isEqualTo(MatchingLevel.HOBBY);
        return this;
    }
}
