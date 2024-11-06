package OrderRentakar.order.service;


import OrderRentakar.order.model.Order;
import OrderRentakar.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String userServiceUrl;

    @Value("${service.vehicule.url}")
    private String vehiculeServiceUrl;

    @Value("${service.license.url}")
    private String licenseServiceUrl;


    //injection
    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }


    //methodes
    public List<Order> getAllOrders() {
        System.out.print("Fetching all Orders ||");
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        System.out.println("Order with id: " + id + " found  ||");
        return orderRepository.findById(id).get();
    }

    public List<Order> getOrderByUserId(int userId) {
        System.out.println("Order with id: " + userId + " found  ||");
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrderByVehiculeId(int vehiculeId) {
        System.out.println("Order with id: " + vehiculeId + " found  ||");
        return orderRepository.findByVehiculeId(vehiculeId);
    }

    public Order saveOrder(Order order) {
        System.out.println("Order with id: " + order.getId() + " saved  ||");
        return orderRepository.save(order);
    }

    public void deleteOrder(int id) {
        System.out.println("Order with id: " + id + " deleted  ||");
        orderRepository.deleteById(id);
    }

    public Order updateOrderById(int id, Order order) {
        Order updatedOrder = orderRepository.findById(id).orElse(null);
        if (updatedOrder != null) {
            orderRepository.deleteById(id);
            updatedOrder.setStartDate(order.getStartDate());
            updatedOrder.setEndDate(order.getEndDate());
            updatedOrder.setVehiculeId(order.getVehiculeId());
            updatedOrder.setStatus(order.getStatus());
            System.out.println("Order with id: " + id + " updated ||");
            return orderRepository.save(updatedOrder);
        } else {
            System.out.println("Order with id: " + id + " not found  ||");
            return null;
        }
    }

    private boolean isUserEligibleByAge(int userId) {
        try { //http://localhost:9090/users
            String url = UriComponentsBuilder
                    .fromHttpUrl(userServiceUrl + "/dateOfBirth/" + userId)
                    .build()
                    .toUriString();

            System.out.println("Age checking for user n°: " + userId);

            ResponseEntity<LocalDate> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<LocalDate>() {
                    }
            );

            LocalDate dateOfBirth = response.getBody();
            if (dateOfBirth == null) {
                System.out.println("User's date of birth not found: " + userId);
                throw new RuntimeException("date of birth not found");
            }

            LocalDate now = LocalDate.now();
            Period age = Period.between(dateOfBirth, now);

            System.out.println("User's age for user n° " + userId + ": " + age.getYears() + " years old");
            return age.getYears() >= 18;

        } catch (Exception e) {
            System.out.println("Age checking error: " + e.getMessage());
            throw new RuntimeException("User's age checking impossible", e);
        }
    }

    private boolean isUserAgeIsMature(int userId) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(userServiceUrl + "/dateOfBirth/" + userId)
                    .build()
                    .toUriString();
            System.out.println("Checking age for user n°: " + userId);
            ResponseEntity<LocalDate> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<LocalDate>() {
                    }
            );
            LocalDate dateOfBirth = response.getBody();
            if (dateOfBirth == null) {
                System.out.println("User's date of birth not found: " + userId);
                throw new RuntimeException("date of birth not found");
            }
            LocalDate now = LocalDate.now();
            Period age = Period.between(dateOfBirth, now);
            System.out.println("User's age " + userId + ": " + age.getYears() + " ans");
            return age.getYears() >= 25;
        } catch (Exception e) {
            System.out.println("Age checking error: " + e.getMessage());
            throw new RuntimeException("Impossible de vérifier l'âge de l'utilisateur", e);
        }
    }

    public Order createOrder(Order order) {
        System.out.println("Attempt to create an order for user: " + order.getUserId());
        if (!isUserEligibleByAge(order.getUserId())) {
            System.out.println("Order creation attempt for a minor user " + order.getUserId());
            throw new RuntimeException("User must be 18 years old to create an order");
        }
        if (!isUserAgeIsMature(order.getUserId()) && (getHorsePowerByVehiculeId(order.getVehiculeId()) > 13)) {
            System.out.println("Order creation attempt for a hig horsePower level with under 25: " + order.getUserId());
            throw new RuntimeException("User must be 25 years old to drive a vehicul with horse power level over 13");
        }
        System.out.println("Order create successfully: " + order.getUserId());
        return orderRepository.save(order);
    }

    public double calculateAmount (Order order) {
        double prixBase = (calculateDayBooked(order.getStartDate(),order.getEndDate())*(getDisplacementByVehiculeId(order.getVehiculeId()/10)));
        double prixKm = (getHorsePowerByVehiculeId(order.getVehiculeId()/100));
    }

    public int getHorsePowerByVehiculeId(int vehiculeId) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(vehiculeServiceUrl + "/horsepower/" + vehiculeId)
                    .build()
                    .toUriString();

            System.out.print("Fetching Horse Power attributes from vehiculeId:" + vehiculeId);
            ResponseEntity<Integer> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Integer>() {
                    }
            );
            int vehiculeHP = response.getBody();
            if (vehiculeHP == 0) {
                System.out.println("Horse Power vehicule is not found: " + vehiculeId);
                throw new RuntimeException("Horse Power not found");
            }
            System.out.println("Vehicule Power is" + vehiculeId + ": " + vehiculeHP + " Horse Power");
            return vehiculeHP;
        } catch (Exception e) {
            System.out.println("Error fetching HOrsePower: " + e.getMessage());
            throw new RuntimeException("Horse Power fetching impossible", e);
        }
    }

    public int getDisplacementByVehiculeId(int vehiculeId) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(vehiculeServiceUrl + "/displacement/" + vehiculeId)
                    .build()
                    .toUriString();

            System.out.print("Fetching Displacement attributes from vehiculeId:" + vehiculeId);
            ResponseEntity<Integer> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Integer>() {
                    }
            );
            int vehiculeDisplacmeent = response.getBody();
            if (vehiculeDisplacmeent == 0) {
                System.out.println("Displacement vehicule is not found: " + vehiculeId);
                throw new RuntimeException("Displacement not found");
            }
            System.out.println("Vehicule Power is" + vehiculeId + ": " + vehiculeDisplacmeent + " Horse Power");
            return vehiculeDisplacmeent;
        } catch (Exception e) {
            System.out.println("Error fetching Displacement: " + e.getMessage());
            throw new RuntimeException("Displacement fetching impossible", e);
        }
    }


    public int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    public int calculateDayBooked(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).getDays();
    }
}
