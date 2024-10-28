package org.TestAptitude.OrderController;

import org.TestAptitude.Service.CamelService;
import org.TestAptitude.Service.OrderMapperService;
import org.TestAptitude.entityInput.ClientOrder;
import org.TestAptitude.entityOutput.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderMapperService orderMapperService;
    private final CamelService camelService;

    @Autowired
    public OrderController(OrderMapperService orderService,CamelService camelService) {
        this.orderMapperService = orderService;
        this.camelService = camelService;
    }

    @PostMapping
    public ResponseEntity<Order> processOrder(@RequestBody ClientOrder clientOrder) {
        Order order = orderMapperService.mapToOrder(clientOrder);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }



    @PostMapping("/camel")
    public ResponseEntity<String> processOrderCamel(@RequestBody ClientOrder clientOrder) {
        camelService.processClientOrder(clientOrder);
        return ResponseEntity.ok("Order processed successfully");
    }


}
