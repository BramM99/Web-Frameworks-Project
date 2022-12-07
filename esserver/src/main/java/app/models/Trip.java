package app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = "Trip_find_current_from_scooter", query = "SELECT t FROM Trip t WHERE t.scooter.status = 'IN-USE'")
public class Trip {
    @Id
    @GeneratedValue
    private long id;

    @JsonManagedReference
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Scooter scooter;

    // Iemand kan maximaal bij één trip horen
    @JsonManagedReference
    @OneToOne(optional = true, cascade = CascadeType.PERSIST)
    private User user;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String startPosition;
    private String endPosition;

    private double mileage;
    private double cost;

    public Trip() {

    }

    public Trip(LocalDateTime startDate, LocalDateTime endDate, String startPosition, String endPosition, double mileage, double cost) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
    }

    public Trip(int id, LocalDateTime startDate, LocalDateTime endDate, String startPosition, String endPosition, double mileage, double cost) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.cost = cost;
    }

    public static Trip createRandomTrip() {
        return new Trip(
                0, LocalDateTime.now(), LocalDateTime.now(), "Amsterdam", "Rotterdam", 12.25, 25.00
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
