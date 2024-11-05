package OrderRentakar.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;


//@JsonFilter("monFiltreDynamique")
@Entity
@Table(name= "`order`")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "userId")
    private int userId;
    @Column(name = "vehiculeId")
    private int vehiculeId;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private double price;
    @Column(name = "kilometers")
    private int kilometreEstimation;


    public Order(int id, int userId, int vehiculeId, LocalDate startDate, LocalDate endDate, String status, double price, int kilometreEstimation) {
        this.id = id;
        this.userId = userId;
        this.vehiculeId = vehiculeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
        this.kilometreEstimation = kilometreEstimation;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getKilometreEstimation() {
        return kilometreEstimation;
    }

    public void setKilometreEstimation(int kilometreEstimation) {
        this.kilometreEstimation = kilometreEstimation;
    }
}
