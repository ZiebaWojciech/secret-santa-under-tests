package team.jit.wojciechzieba.secretsantaundertests;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participants")
class ParticipantsController {

    public ParticipantsController(final ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }

    private final ParticipantsRepository participantsRepository;

    @GetMapping
    List<ParticipantDto> getAllParticipants(){
        return participantsRepository.getAll()
                .stream()
                .map(ParticipantDto::from)
                .toList();
    }

    @PostMapping
    String createParticipant(
            @RequestBody ParticipantDto participant
    ) {
        if (participant.id() != null) {
            throw new IllegalArgumentException();
        }

        return participantsRepository.save(participant.toDomain());
    }
}
