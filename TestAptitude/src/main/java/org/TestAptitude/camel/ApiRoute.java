package org.TestAptitude.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiRoute extends RouteBuilder {
    //app.properties
    @Value("${api.url.produits}")
    private String produitsApiUrl;

    @Value("${api.url.commandes}")
    private String commandesApiUrl;

    @Value("${api.url.products}")
    private String productsApiUrl;

    @Override
    public void configure() {
        // route pour les produits
        // filtre pour recuperer les produits via query
        from("direct:getProduits") //depart
                .routeId("getProduitsRoute")
                // j'ai choisie de filtrer sur categorie
                .setHeader("CamelHttpMethod", constant("GET"))
                .setHeader("CamelHttpUri", simple(produitsApiUrl + "?categorie=${header.categorie}"))
                .toD("http:${header.CamelHttpUri}") // effectuer la requete GET avec camel-http // toD permet de construire dynamiquement l uri => c'est la destination
                .unmarshal().json(JsonLibrary.Jackson)
                .log("produits récupérés : ${body}");

        // route pour les commandes
        from("direct:getCommandes")
                .routeId("getCommandesRoute")
                .setHeader("CamelHttpMethod", constant("GET"))
                // j'ai choisie de filtrer sur la date
                .setHeader("CamelHttpUri", simple(commandesApiUrl + "?date=${header.date}"))  //passer les parametres via l entete du message
                .toD("http:${header.CamelHttpUri}")
                .unmarshal().json(JsonLibrary.Jackson)
                .log("commandes récupérées : ${body}");

// je ne peux pas testé car réellement les 2 api externe n'existe pas.

        // route pour créer un produit (post)
        from("direct:createProduct")
                .routeId("createProductRoute") //creation de la nouvelle route camel qui permet l 'insertion d'un produit
                .setHeader("CamelHttpMethod", constant("POST")) // choix de methode utilisé post
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson)
                .toD(productsApiUrl)
                .log("produit crée : ${body}");
    }
}
