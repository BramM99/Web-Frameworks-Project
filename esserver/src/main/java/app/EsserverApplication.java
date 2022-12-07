package app;

import app.models.Scooter;
import app.models.Trip;
import app.models.User;
import app.repositories.ScootersRepositoryJpa;
import app.repositories.TripsRepositoryJpa;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
public class EsserverApplication implements CommandLineRunner {

	@Autowired
	private ScootersRepositoryJpa scooterRepo;
	@Autowired
	private TripsRepositoryJpa tripRepo;
	@Autowired
	private UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(EsserverApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running CommandLine Startup");
		this.createInitialScooters();
	}

	private void createInitialScooters() {
		List<Scooter> scooters = this.scooterRepo.findAll();
		if (scooters != null && scooters.size() > 0) return;
		System.out.println("Configuring some initial scooter data");

		for (int i = 0; i < 7; i++) {
			Scooter scooter = Scooter.createRandomScooter();
			//Vooraf aangemaakt trips gestopt voor duidelijkheid over eigen gemaakte Trip
			// Trip trip = Trip.createRandomTrip();
			//
			// scooter.addTrip(trip);

			Scooter savedScooter = this.scooterRepo.save(scooter);
		}

		// Test gebruiker aanmaken
		User user = new User(1, "Naam", "Email", "Wachtwoord", false,false);
		userRepo.save(user);
	}

}
