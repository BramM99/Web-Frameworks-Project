package app.repositories;

import app.models.Scooter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScooterRepositoryMock implements ScooterRepository {

    private static final List<Scooter> scooters = new ArrayList<>();
    private static int uniqueId = 3001;

    static {
        for (int i = 0; i < 8; i++) {
            addRandomScooter();
        }
    }

    public List<Scooter> findAll() {
        return scooters;
    }

    public Scooter findById(int oId) {
        for (Scooter scooter : scooters) {
            if (scooter.getId() == oId) {
                return scooter;
            }
        }
        return null;
    }

    public Scooter save(Scooter saveScooter) {
        if (saveScooter.getId() == 0) {
            int highestId = scooters.get(scooters.size() - 1).getId();
            saveScooter.setId(highestId + 1);
        }

        boolean copy = false;

        //Al een bestaande ID
        for (Scooter scooter : scooters) {
            if (scooter.getId() == saveScooter.getId()) {
                scooters.set(scooters.indexOf(scooter), saveScooter);
                copy = true;
            }
        }

        //Een nieuwe ID
        if (!copy) {
            scooters.add(scooters.size(), saveScooter);
        }
        return saveScooter;
    }

    public Scooter deleteById(int oId) {
        Scooter foundScooter = this.findById(oId);
        if (foundScooter == null) {
            return null;
        } else {
            scooters.remove(foundScooter);
            return foundScooter;
        }
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        return null;
    }

    public static void addRandomScooter() {
        Scooter newScooter = Scooter.createRandomScooter();
        newScooter.setId(uniqueId++);
        scooters.add(newScooter);
    }

}
