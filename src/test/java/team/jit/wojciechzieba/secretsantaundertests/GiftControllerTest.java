package team.jit.wojciechzieba.secretsantaundertests;

import org.junit.jupiter.api.Test;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    private final static String VALID_REQUEST = """
                {
                    "gifter": "Maciej",
                    "colour": "niebieski",
                    "gift": "dodatkowe zadanie",
                    "relatedHobby": "nadgodziny"
                }
                """;

    @Test
    void shouldReturnOk_andMatchedPersonName_whenGiftMatched() throws Exception {
        // given
        doReturn("Wojtek")
                .when(giftService).registerGift(any());

        // when
        var result = mockMvc.perform(
                        post("/gifts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(VALID_REQUEST)
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
        doThrow(GifterNotFoundException.class)
                .when(giftService).registerGift(any());

        // when-then
        mockMvc.perform(
                        post("/gifts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(VALID_REQUEST)
                ).andExpect(
                        status().isNotFound()
        );
    }

    @Test
    void shouldReturnBadRequest_whenTooFewPeopleRegistered() throws Exception {
        // given

        doThrow(TooFewParticipantsException.class)
                .when(giftService).registerGift(any());

        // when-then
        mockMvc.perform(
                post("/gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_REQUEST)
        ).andExpect(
                status().isBadRequest()
        );
    }
}
