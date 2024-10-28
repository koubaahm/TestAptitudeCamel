package org.TestAptitude.entityInput;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor@Getter@Setter
public class Contenu {

    @JsonProperty("type_message")
    private String typeMessage;

    private int id;
    private Fournisseur fournisseur;

    @JsonProperty("site_reception")
    private SiteReception siteReception;

    @JsonProperty("numero_commande")
    private String numeroCommande;

    @JsonProperty("numero_livraison")
    private String numeroLivraison;
    private String statut;
    private String creation;
    private String modification;

    @JsonProperty("date_reception")
    private String dateReception;

    private List<Ligne> lignes;
}

