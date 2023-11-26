package team.jit.wojciechzieba.secretsantaundertests;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
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
        doReturn(new GiftRegistrationResponse("Wojtek", MatchingLevel.NONE))
                .when(giftService).registerGift(any());

        // when
        RestAssuredMockMvc
                .given()
                .mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(VALID_REQUEST)
                .when()
                .post("/gifts")
                .then()
                .body("name", is("Wojtek"))
                .body("matchingLevel", is("NONE"));
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
