package team.jit.wojciechzieba.secretsantaundertests;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("participant")
@AllArgsConstructor
public class Participant {

    @Id
    String id;
    String name;
    String hobby;
    String favouriteColour;
    boolean gifted;
    @Version
    Integer version;
}
