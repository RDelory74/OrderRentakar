package OrderRentakar.order.controler;


import OrderRentakar.order.model.Order;
import OrderRentakar.order.repository.OrderRepository;
import OrderRentakar.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderControler {
    private final OrderService orderService;

    public OrderControler(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{id}")
    public Order showOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/order")
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/order/{id}")
    public Order updateOrder(@PathVariable int id, @RequestBody Order order) {
        return orderService.updateOrderById(id, order);
    }
    @DeleteMapping("order/{id}")
    public Order deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return null;
    }
    @GetMapping("/orders/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable int userId) {
        return orderService.getOrderByUserId(userId);

    }
}
