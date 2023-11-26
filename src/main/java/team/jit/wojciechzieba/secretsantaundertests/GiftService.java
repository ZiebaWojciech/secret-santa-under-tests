package team.jit.wojciechzieba.secretsantaundertests;

class GiftService {
    private final ParticipantsRepository participantsRepository;
    public GiftService(final ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }

    public String registerGift(final GiftRegistrationCommand giftRegistrationCommand) {
        return "Wojtek";
    }
}
