package org.TestAptitude.camel;

import org.TestAptitude.entityInput.ClientOrder;
import org.TestAptitude.entityOutput.Order;
import org.TestAptitude.service.OrderMapperService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends RouteBuilder {

    private final OrderMapperService orderMapperService;


    public OrderRoute(OrderMapperService orderMapperService) {
        this.orderMapperService = orderMapperService;
    }

    @Override
    public void configure() {
        // route principale
        from("direct:processClientOrder")
                .routeId("commandeRoute")
                .unmarshal().json(JsonLibrary.Jackson, ClientOrder.class) // deserialisation de json a ClientOrder
                //le mapping avec camel  de json vers objet java
                .process(exchange -> {
                    ClientOrder clientOrder = exchange.getIn().getBody(ClientOrder.class);
                    if (clientOrder == null) {
                        throw new RuntimeException("clientOrder est null");
                    }


                    Order order = orderMapperService.mapToOrder(clientOrder); // utiliser le mapping de la question 1

                    exchange.getIn().setBody(order);
                })
                .to("direct:processOrder");

        // route commande
        from("direct:processOrder")
                .routeId("traitementCommandeRoute") //nom route
                .log(" la commande : ${body}")
                .marshal().json(JsonLibrary.Jackson);

        // route lignes de commande
        from("direct:processOrderItem")
                .routeId("traitementLigneCommandeRoute")
                .log(" la ligne de commande : ${body}");
    }
}
