package org.TestAptitude.entityOutput;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class OrderItem {

    private String id;

    @JsonProperty("idpurchaseorder")
    private int idPurchaseOrder;

    @JsonProperty("idproducts")
    private String idProducts;

    @JsonProperty("quantityorder")
    private String quantityOrder;

    @JsonProperty("branchs_id")
    private int branchsId;
}

