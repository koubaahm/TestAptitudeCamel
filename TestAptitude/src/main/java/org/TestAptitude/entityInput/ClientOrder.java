package org.TestAptitude.entityInput;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ClientOrder {

    private int id;

    @JsonProperty("message_type")
    private String messageType;

    private String creation;
    private int exported;
    private Contenu contenu;
}
