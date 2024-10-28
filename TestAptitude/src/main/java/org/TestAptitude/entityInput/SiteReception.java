package org.TestAptitude.entityInput;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data@NoArgsConstructor@AllArgsConstructor@Setter@Getter
public class SiteReception {

    @JsonProperty("site_id")
    private int siteId;

    @JsonProperty("nom_site")
    private String nomSite;
    private String telephone;
}

