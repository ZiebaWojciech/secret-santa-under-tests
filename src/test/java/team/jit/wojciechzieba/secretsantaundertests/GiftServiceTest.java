package team.jit.wojciechzieba.secretsantaundertests;

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
                ParticipantBuilder.participant()
                        .withName("Maciej")
                        .withHobby("absynt")
                        .withFavouriteColour("zielony")
                        .build()
        ).when(participantsRepositoryMock).getByName("Maciej");

        GiftRegistrationCommand giftRegistrationCommand = new GiftRegistrationCommand(
                "Maciej",
                "czarny",
                "dodatkowe zadanie",
                "nadgodziny"
                );


        // when
        var result = sut.registerGift(giftRegistrationCommand);

        // then
        Assertions.assertThat(result).isEqualTo("Wojtek");

    }
}