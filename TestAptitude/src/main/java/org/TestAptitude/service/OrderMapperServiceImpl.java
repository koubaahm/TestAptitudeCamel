package org.TestAptitude.service;

import org.TestAptitude.entityInput.ClientOrder;
import org.TestAptitude.entityInput.Ligne;
import org.TestAptitude.entityOutput.Order;
import org.TestAptitude.entityOutput.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapperServiceImpl implements OrderMapperService {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderMapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // mapping avec model mapper
    @Override
    public Order mapToOrder(ClientOrder clientOrder) {

        System.out.println("Mapping ClientOrder: " + clientOrder);
        Order order = modelMapper.map(clientOrder.getContenu(), Order.class);


        order.setContactId(clientOrder.getContenu().getFournisseur().getCode());
        order.setContactName(clientOrder.getContenu().getFournisseur().getNom().trim());
        order.setBranchName(clientOrder.getContenu().getSiteReception().getNomSite().trim());
        order.setReference(clientOrder.getContenu().getNumeroCommande().trim());
        order.setBranchsId(clientOrder.getContenu().getSiteReception().getSiteId());
        order.setWeight("0.000000"); //j'ai supposé que c'est 0 par defaut
        order.setDateOrder(clientOrder.getContenu().getCreation());
        order.setDateReceiveEstimated(clientOrder.getContenu().getDateReception() + "T00:00:00+0000"); // Format de date
        order.setUserText5(clientOrder.getContenu().getTypeMessage());


        String quantity = String.valueOf(clientOrder.getContenu().getLignes().stream()
                .mapToInt(Ligne::getQuantite)
                .sum());
        order.setQuantity(quantity + ".000000");
        order.setQuantityReceive(order.getQuantity());

        // les lignes de commande
        List<OrderItem> orderItems = clientOrder.getContenu().getLignes().stream()
                .map(ligne -> {
                    OrderItem orderItem = modelMapper.map(ligne, OrderItem.class);
                    orderItem.setIdPurchaseOrder(clientOrder.getContenu().getId());
                    orderItem.setIdProducts(ligne.getCodeProduit()); // mapping des produits
                    orderItem.setQuantityOrder(quantity + ".000000");
                    orderItem.setBranchsId(clientOrder.getContenu().getSiteReception().getSiteId());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);
        return order;
    }
}

// methode classic du mapping avec les getters et setteurs

/*
@Override
public Order mapToOrder(ClientOrder clientOrder) {


    Order order = modelMapper.map(clientOrder.getContenu(), Order.class);

    // mapping des champs spécifiques
    order.setContactId(clientOrder.getContenu().getFournisseur().getCode());
    order.setContactName(clientOrder.getContenu().getFournisseur().getNom().trim());
    order.setBranchName(clientOrder.getContenu().getSiteReception().getNomSite().trim());
    order.setReference(clientOrder.getContenu().getNumeroCommande().trim());
    order.setBranchsId(clientOrder.getContenu().getSiteReception().getSiteId());
    order.setWeight("0.000000");
    order.setDateOrder(clientOrder.getContenu().getCreation());
    order.setDateReceiveEstimated(clientOrder.getContenu().getDateReception() + "T00:00:00+0000");
    order.setUserText5(clientOrder.getContenu().getTypeMessage());

    // calcul de la quantité
    String quantity = String.valueOf(clientOrder.getContenu().getLignes().stream()
            .mapToInt(Ligne::getQuantite)
            .sum());
    order.setQuantity(quantity + ".000000");
    order.setQuantityReceive(order.getQuantity());

    // mapping des lignes de commande
    List<OrderItem> orderItems = clientOrder.getContenu().getLignes().stream()
            .map(ligne -> {
                OrderItem orderItem = modelMapper.map(ligne, OrderItem.class);
                orderItem.setIdPurchaseOrder(clientOrder.getContenu().getId());
                orderItem.setIdProducts(ligne.getCodeProduit()); // mapping du produit
                orderItem.setQuantityOrder(quantity + ".000000");
                orderItem.setBranchsId(clientOrder.getContenu().getSiteReception().getSiteId());
                return orderItem;
            })
            .collect(Collectors.toList());

    order.setItems(orderItems);

    return order;
}*/
