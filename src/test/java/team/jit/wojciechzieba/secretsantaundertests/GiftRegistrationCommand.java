package team.jit.wojciechzieba.secretsantaundertests;

public record GiftRegistrationCommand(
        String gifterName,
        String giftColour,
        String gift,
        String relatedHobby) {
}
