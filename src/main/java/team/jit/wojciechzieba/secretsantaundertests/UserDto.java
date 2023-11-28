package team.jit.wojciechzieba.secretsantaundertests;

record UserDto(String name, String hobby, String favouriteColour) {
    public Participant toParticipant() {
        return new Participant(
                null,
                name,
                hobby,
                favouriteColour,
                false,
                null
        );
    }
}
