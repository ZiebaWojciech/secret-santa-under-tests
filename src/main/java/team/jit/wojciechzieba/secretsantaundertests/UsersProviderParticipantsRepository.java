package team.jit.wojciechzieba.secretsantaundertests;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

class UsersProviderParticipantsRepository implements ParticipantsRepository {
    public UsersProviderParticipantsRepository(final WebClient webClient) {
        this.webClient = webClient;
    }

    private final WebClient webClient;

    @Override
    public List<Participant> getAll() {
        return Arrays.stream(
                        webClient.get()
                                .uri("http://localhost:9999/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(UserDto[].class)
                                .block())
                .map(UserDto::toParticipant)
                .toList();
    }

    @Override
    public String save(final Participant participant) {
        return null;
    }
}
