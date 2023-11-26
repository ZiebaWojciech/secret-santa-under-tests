package team.jit.wojciechzieba.secretsantaundertests;

class GiftService {
    private final ParticipantsRepository participantsRepository;

    public GiftService(final ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }

    public GiftRegistrationResponse registerGift(final GiftRegistrationCommand giftRegistrationCommand) {

        return participantsRepository.getAll()
                .stream()
                .filter(participant -> participant.hobby.equals(giftRegistrationCommand.relatedHobby()))
                .findAny()
                .map(participant -> new GiftRegistrationResponse(participant.name, MatchingLevel.HOBBY))
                .orElseThrow(CannotFindMatchException::new);
    }
}


