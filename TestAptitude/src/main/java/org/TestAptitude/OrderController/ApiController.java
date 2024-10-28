package org.TestAptitude.OrderController;


import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProducerTemplate producerTemplate;

    public ApiController(CamelContext camelContext) {
        this.producerTemplate = camelContext.createProducerTemplate();
    }

    @GetMapping("/produits")
    public String getProduits(@RequestParam String categorie) {
        return producerTemplate.requestBodyAndHeader("direct:getProduits", null, "categorie", categorie, String.class);
    }

    @GetMapping("/commandes")
    public String getCommandes(@RequestParam String date) {
        return producerTemplate.requestBodyAndHeader("direct:getCommandes", null, "date", date, String.class);
    }
}

