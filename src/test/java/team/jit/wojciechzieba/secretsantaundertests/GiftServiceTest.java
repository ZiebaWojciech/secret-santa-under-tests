package team.jit.wojciechzieba.secretsantaundertests;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static team.jit.wojciechzieba.secretsantaundertests.ParticipantTestFactory.*;

@ExtendWith(MockitoExtension.class)
class GiftServiceTest {

    @Mock
    private ParticipantsRepository participantsRepositoryMock;

    private GiftService sut;

    @BeforeEach
    void setup() {
        sut = new GiftService(
                participantsRepositoryMock
        );
    }

    @Test
    void shouldReturnParticipantWithSameHobby_whenGiftWithHobbySentInRequest() {
        // given
        doReturn(
                List.of(
                        MACIEJ_ABSYNTH_ENJOYER,
                        ParticipantBuilder.participant()
                                .withName("Matylda")
                                .withHobby("nadgodziny")
                                .withFavouriteColour("czarny")
                                .build()
                )
        ).when(participantsRepositoryMock).getAll();

        GiftRegistrationCommand giftRegistrationCommand = new GiftRegistrationCommand(
                "Maciej",
                "czarny",
                "dodatkowe zadanie",
                "nadgodziny"
        );

        // when
        var result = sut.registerGift(giftRegistrationCommand);

        // then
        Assertions.assertThat(result.name()).isEqualTo("Matylda");
        Assertions.assertThat(result.matchingLevel()).isEqualTo(MatchingLevel.HOBBY);
    }
}