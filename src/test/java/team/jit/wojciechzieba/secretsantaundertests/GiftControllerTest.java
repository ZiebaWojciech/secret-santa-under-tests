package team.jit.wojciechzieba.secretsantaundertests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GiftController.class)
class GiftControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GiftService giftService;

    @Test
    void shouldReturnOk_andMatchedPersonName_whenGiftMatched() throws Exception {
        // given
        var giftRegistrationRequest = """
                {
                    "gifter": "Maciej",
                    "colour": "niebieski",
                    "gift": "dodatkowe zadanie",
                    "relatedHobby": "nadgodziny"
                }
                """;

        doReturn("Wojtek")
                .when(giftService).registerGift();

        // when
        var result = mockMvc.perform(
                        post("/gifts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(giftRegistrationRequest)
                ).andExpect(
                        status().isOk()
                ).andReturn()
                .getResponse();

        // then
        assertThat(result.getContentAsString()).isEqualTo("Wojtek");
    }


    @Test
    void shouldReturnBadRequest_whenGifterNotFound() throws Exception {
        // given
        var giftRegistrationRequest = """
                {
                    "gifter": "Maciej",
                    "colour": "niebieski",
                    "gift": "dodatkowe zadanie",
                    "relatedHobby": "nadgodziny"
                }
                """;

        doThrow(GifterNotFoundException.class)
                .when(giftService).registerGift();

        // when-then
        mockMvc.perform(
                        post("/gifts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(giftRegistrationRequest)
                ).andExpect(
                        status().isNotFound()
        );
    }

    @Test
    void shouldReturnBadRequest_whenTooFewPeopleRegistered() throws Exception {
        // given
        var giftRegistrationRequest = """
                {
                    "gifter": "Maciej",
                    "colour": "niebieski",
                    "gift": "dodatkowe zadanie",
                    "relatedHobby": "nadgodziny"
                }
                """;

        doThrow(TooFewParticipantsException.class)
                .when(giftService).registerGift();

        // when-then
        mockMvc.perform(
                post("/gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftRegistrationRequest)
        ).andExpect(
                status().isBadRequest()
        );
    }
}
