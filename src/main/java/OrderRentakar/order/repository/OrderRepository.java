package OrderRentakar.order.repository;


import OrderRentakar.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("Select o From Order o where o.userId=:userId ")
    List<Order> findByUserId(int userId);
}
