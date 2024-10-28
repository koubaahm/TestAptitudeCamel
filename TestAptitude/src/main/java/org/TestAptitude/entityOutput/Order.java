package org.TestAptitude.entityOutput;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Order {

    private int id;

    @JsonProperty("contact_id")
    private String contactId;

    private String reference;

    @JsonProperty("datevalidationprovider")
    private Object dateValidationProvider;

    @JsonProperty("dateorder")
    private String dateOrder;

    @JsonProperty("datereceive_estimated")
    private String dateReceiveEstimated;

    @JsonProperty("branchs_id")
    private int branchsId;

    @JsonProperty("user_text_5")
    private String userText5;

    private String weight;

    @JsonProperty("branch_name")
    private String branchName;

    private String quantity;

    @JsonProperty("quantityreceive")
    private String quantityReceive;

    @JsonProperty("contact_name")
    private String contactName;

    private List<OrderItem> items;
}

