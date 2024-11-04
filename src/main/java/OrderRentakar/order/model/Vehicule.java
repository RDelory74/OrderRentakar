package OrderRentakar.order.model;

public class Vehicule {
    private int id;
    private String registrationNumber;
    private String model;
    private String color;
    private String type;
    private int kilometers;


    public Vehicule(int id, String registrationNumber, String model, String color, String type, int kilometers) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.color = color;
        this.type = type;
        this.kilometers = kilometers;
    }

    public Vehicule() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }
}
