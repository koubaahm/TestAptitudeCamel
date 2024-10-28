package org.TestAptitude.entityInput;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Ligne {

    private String id;

    @JsonProperty("code_produit")
    private String codeProduit;

    @JsonProperty("libelle_fr")
    private String libelleFr;
    private int quantite;
    private String unite;
    private int lieu;

    @JsonProperty("nb_jour_dlc_apres_decongelation")
    private int nbJourDlcApresDecongelation;

    @JsonProperty("nb_jour_dlv")
    private int nbJourDlv;

    @JsonProperty("nb_jour_blocage")
    private int nbJourBlocage;
    private boolean fragile;

    @JsonProperty("numero_lot")
    private String numeroLot;
    private String dlc;
    private String categorie;
}

