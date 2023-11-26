package team.jit.wojciechzieba.secretsantaundertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GiftController.class)
class GiftControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnOk_andMatchedPersonName_whenGiftMatched() throws Exception {
        // given
        var giftRegistrationRequest = """
                {
                    "gifter": "Maciej",
                    "colour": "niebieski",
                    "gift": "dodatkowe zadanie",
                    §"relatedHobby": "nadgodziny"
                }
                """;

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
}
