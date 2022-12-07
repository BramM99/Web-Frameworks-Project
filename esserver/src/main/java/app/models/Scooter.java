package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@NamedQueries({
        @NamedQuery(name = "Scooter_find_by_status", query = "SELECT s FROM Scooter s WHERE s.status= ?1"),
        @NamedQuery(name = "Scooter_find_by_battery", query = "SELECT s FROM Scooter s WHERE s.batteryCharge < ?1")
})
public class Scooter {

    @Id
    @GeneratedValue
    @JsonView(View.ScooterSummary.class)
    private int id;

    @JsonBackReference
    @OneToMany(mappedBy = "scooter", cascade = CascadeType.ALL)
    private List<Trip> trips = new ArrayList<Trip>();

    @JsonView(View.ScooterSummary.class)
    private String tag;

    @JsonView(View.ScooterSummary.class)
    private String status;

    private String gpsLocation;

    private int mileage;

    @JsonView(View.ScooterSummary.class)
    private int batteryCharge;

    public Scooter() {

    }

    public Scooter(String tag, String status, String gpsLocation, int mileage, int batteryCharge) {
        super();
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    public Scooter(int id, String tag, String status, String gpsLocation, int mileage, int batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }

    public static Scooter createRandomScooter() {
        return new Scooter(0,
                generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 8),
                createRandomStatus(),
                "52.369485, 4.846955",
                createRandomNumber(200),
                5 + createRandomNumber(100));
    }

    private static String createRandomStatus() {
        int random = createRandomNumber(2);
        if (random == 0) {
            return "IDLE";
        } else if (random == 1) {
            return "IN-USE";
        } else {
            return "MAINTENANCE";
        }
    }

    /**
     * creates a random number using a multiplier and makes sure its rounded
     */
    public static int createRandomNumber(int multiply) {
        return (int) Math.floor(Math.random() * multiply);
    }

    public static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }

    public Trip startNewTrip(LocalDateTime startDateTime) {
        return null;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
        trip.setScooter(this);
    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", status=" + status +
                ", gpsLocation='" + gpsLocation + '\'' +
                ", mileage=" + mileage +
                ", batteryCharge=" + batteryCharge +
                '}';
    }
}
