package app.rest;

import app.models.Scooter;
import app.models.Trip;
import app.models.User;
import app.models.View;
import app.repositories.*;
import app.utility.JWTRequestFilter;
import app.utility.JWTToken;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.PersistenceContext;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ScooterController {
    @Autowired
    private ScooterRepository scooterRepo;

    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private UserRepository userRepo;

    @Value("${jwt.pass-phrase}")
    private String passPhrase;

    @GetMapping(path = "/scooters")
    public List<Scooter> getAllScooters(@RequestParam Map<String, String> params) {
        if (params.size() > 1) {
            throw new ScooterPreConditionFailed("At most one parameter can be provided!");
        } else if (params.containsKey("battery")) {
            String value = params.get("battery");
            System.out.println("BATTERY WORDT OPGEHAALD MET VALUE: " + value);
            return scooterRepo.findByQuery("Scooter_find_by_battery", value);
        } else if (params.containsKey("status")) {
            String value = params.get("status");
            return scooterRepo.findByQuery("Scooter_find_by_status", value);
        } else {
            return scooterRepo.findAll();

        }
    }

    @JsonView(View.ScooterSummary.class)
    @GetMapping(path = "/scooters/summary")
    public List<Scooter> getAllScootersSummary() {
        return scooterRepo.findAll();
    }

    @GetMapping(path = "/scooters/{id}")
    public Scooter getScooterById(@PathVariable int id) {
        Scooter scooter = scooterRepo.findById(id);
        if (scooter == null) {
            throw new ScooterNotFoundException("id-" + id);
        }
        return scooter;
    }

    @PostMapping(path = "/scooters")
    public ResponseEntity<Scooter> createScooter(@RequestBody Scooter scooter) {
        scooterRepo.save(scooter);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(scooter.getId())
                .toUri();

        return ResponseEntity.created(location).body(scooter);
    }

    @PutMapping(path = "/scooters/{id}")
    public Scooter editScooterById(@PathVariable int id, @RequestBody Scooter newScooter) {
        Scooter oldScooter = scooterRepo.findById(id);
        if (newScooter.getId() != oldScooter.getId()) {
            throw new ScooterPreConditionFailed("id-" + id);
        }
        newScooter.setId(oldScooter.getId());
        return scooterRepo.save(newScooter);
    }

    @PostMapping(path = "/scooters/{id}/claim")
    public Trip addTrip(@PathVariable int id, @RequestBody LocalDateTime startDate, HttpServletRequest request) {
        JWTToken token = (JWTToken) request.getAttribute("token");
        long userId = token.getUserId();

        Trip trip = new Trip();
        Scooter scooter = scooterRepo.findById(id);
        User user = userRepo.findById(userId);

        if ((!scooter.getStatus().equals("IDLE")) || (scooter.getBatteryCharge() < 10)) {
            throw new ScooterPreConditionFailed("id-" + id);
        }

        trip.setStartDate(Objects.requireNonNullElseGet(startDate, LocalDateTime::now));
        trip.setStartPosition(scooter.getGpsLocation());
        trip.setEndDate(null);
        trip.setEndPosition(null);
        trip.setScooter(scooter);
        trip.setUser(user);

        tripRepo.save(trip);
        scooter.addTrip(trip);

        return trip;
    }

    @DeleteMapping(path = "/scooters/{id}")
    public Scooter deleteScooterById(@PathVariable int id) {
        Scooter scooter = scooterRepo.findById(id);
        if (scooter == null) {
            throw new ScooterNotFoundException("id-" + id);
        }
        return scooterRepo.deleteById(id);
    }

    @GetMapping(path = "/scooters/currenttrips")
    public List<Trip> ScooterByTrips() {
        return tripRepo.findByQuery("Trip_find_current_from_scooter");
    }
}
