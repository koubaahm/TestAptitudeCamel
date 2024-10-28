package org.TestAptitude.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.TestAptitude.entityInput.ClientOrder;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class CamelServiceImpl implements CamelService {

    private final CamelContext camelContext;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public CamelServiceImpl(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void processClientOrder(ClientOrder clientOrder) {
        String jsonInput = convertClientOrderToJson(clientOrder);
        System.out.println("envoi de la commande client: " + jsonInput);
        sendOrderToCamel(jsonInput);
    }

    private String convertClientOrderToJson(ClientOrder clientOrder) {
        try {
            return objectMapper.writeValueAsString(clientOrder);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erreur lors de la conversion de ClientOrder en JSON", e);
        }
    }

    private void sendOrderToCamel(String jsonInput) {
        ProducerTemplate template = camelContext.createProducerTemplate();
        template.sendBody("direct:processClientOrder", jsonInput);
    }
}
