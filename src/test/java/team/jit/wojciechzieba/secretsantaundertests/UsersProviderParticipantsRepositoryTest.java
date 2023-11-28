package team.jit.wojciechzieba.secretsantaundertests;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersProviderParticipantsRepositoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClientMock;

    private ParticipantsRepository sut = new UsersProviderParticipantsRepository(webClientMock);

    @Test
    @Disabled
    void shouldReturnAllParticipants_whenOtherServiceIsCalled() {
        // given
        List<UserDto> USERS =
                List.of(
                        new UserDto("Piotr",
                                "zdrada aka samorozwój",
                                "biały"
                        ),

                        new UserDto("Wojtek",
                                "imprezy integracyjne",
                                "niebieski")
                        ,
                        new UserDto(
                                "Matylda",
                                "ass-kicking",
                                "czarny"
                        ),
                        new UserDto(
                                "Maciej",
                                "absynt",
                                "zielony"
                        ),
                        new UserDto(
                                "Domżi",
                                "klocki lego",
                                "piwny"
                        ),
                        new UserDto(
                                "Muody",
                                "praca po godzinach",
                                "tani"
                        )
                );

        when(webClientMock.get()
                .uri("/users")
                .retrieve()
                .bodyToMono(UserDto[].class)
                .block()
        ).thenReturn(USERS.toArray(new UserDto[0]));
//        when(requestHeadersUriMock.uri("/users"))
//                .thenReturn(requestHeadersSpecMock);
//        when(requestHeadersMock.retrieve())
//                .thenReturn(responseSpecMock);
//        when(responseMock.bodyToMono(List.class))
//                .thenReturn(Mono.just(USERS));


        // when
        var result = sut.getAll();

        // then
        Assertions.assertThat(result).size().isEqualTo(USERS.size());

    }


}