package org.TestAptitude.camel;


import org.TestAptitude.Service.OrderMapperServiceImpl;
import org.TestAptitude.entityInput.ClientOrder;
import org.TestAptitude.entityOutput.Order;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessusCompletRoute extends RouteBuilder {
    @Value("${api.url.produits}")
    private String produitsApiUrl;

    @Value("${api.url.commandes}")
    private String commandesApiUrl;

    @Value("${api.url.creerProduit}")
    private String creerProduitApiUrl;

    private final OrderMapperServiceImpl orderMapper;

    public ProcessusCompletRoute(OrderMapperServiceImpl orderMapper) {
        this.orderMapper = orderMapper;
    }
    @Override
    public void configure() {

        // 1 recupération des commandes depuis https://montest.com/api/commandes
        from("direct:fetchOrders")
                .routeId("fetchOrdersRoute")
                .setHeader("CamelHttpMethod", constant("GET"))
                .setHeader("CamelHttpUri", simple(commandesApiUrl))
                .toD("http:${header.CamelHttpUri}")
                .unmarshal().json(JsonLibrary.Jackson, ClientOrder.class) // conversion du json vers l objet java en utilisant la bibliotheque jackson
                .log("commandes récupérées : ${body}")
                //le mapping de l'input vers l'output avec camel
                .process(exchange -> {
                    ClientOrder clientOrder = exchange.getIn().getBody(ClientOrder.class);
                    Order order = orderMapper.mapToOrder(clientOrder);
                    exchange.getIn().setBody(order);
                })
                .log("Commande après mapping : ${body}") // j'utilise les logs pour voir si le mapping a eu lieu avec le bon format attendu de l'objet output
                .to("direct:createProduct"); // passe à la route qui permet la creation d' un produit

        // 2 création  de produits dans https://montest.com/api/products
        from("direct:createProduct")
                .routeId("createProductRoute")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("CamelHttpMethod", constant("POST")) //utilisation de la methode post pour la creation
                .setHeader("CamelHttpUri", constant(creerProduitApiUrl))
                .to("http://${header.CamelHttpUri}")
                .log("le produit cree est : ${body}");
    }
}

// ici on recupere l'objet input , on fait le mapping vers l'objet output et en suite la creation d'objet (ou modification ) dans l' api https://montest.com/api/products