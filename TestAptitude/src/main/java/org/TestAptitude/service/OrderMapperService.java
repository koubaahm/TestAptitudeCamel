package org.TestAptitude.service;


import org.TestAptitude.entityInput.ClientOrder;
import org.TestAptitude.entityOutput.Order;

public interface OrderMapperService {
    Order mapToOrder(ClientOrder clientOrder);
}

