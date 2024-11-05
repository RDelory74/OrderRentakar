package OrderRentakar.order.service;


import OrderRentakar.order.model.Order;
import OrderRentakar.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String orderServiceUrl;

    @Value("${service.vehicule.url}")
    private String vehicleServiceUrl;

    @Value("${service.license.url}")
    private String licenseServiceUrl;


    //injection
    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }


    //methodes
    public List<Order> getAllOrders(){
        System.out.print("Fetching all Orders ||");
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        System.out.println("Order with id: " + id + " found  ||");
        return orderRepository.findById(id).get();
    }

    public List<Order> getOrderByUserId(int userId){
        System.out.println("Order with id: " + userId + " found  ||");
        return orderRepository.findByUserId(userId);
    }

    public Order saveOrder(Order order){
        System.out.println("Order with id: " + order.getId() + " saved  ||");
        return orderRepository.save(order);
    }
    public void deleteOrder(int id){
        System.out.println("Order with id: " + id + " deleted  ||");
        orderRepository.deleteById(id);
    }
    public Order updateOrderById (int id, Order order){
        Order updatedOrder = orderRepository.findById(id).orElse(null);
        if ( updatedOrder != null ) {
            orderRepository.deleteById(id);
            updatedOrder.setStartDate(order.getStartDate());
            updatedOrder.setEndDate(order.getEndDate());
            updatedOrder.setVehiculeId(order.getVehiculeId());
            updatedOrder.setStatus(order.getStatus());
            System.out.println("Order with id: " + id + " updated ||");
            return orderRepository.save(updatedOrder);
        }else {
            System.out.println("Order with id: " + id + " not found  ||");
            return null;
        }
    }

}
