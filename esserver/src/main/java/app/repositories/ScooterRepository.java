package app.repositories;

import app.models.Scooter;

import java.util.List;

public interface ScooterRepository {
    List<Scooter> scooters = null;

    public List<Scooter> findAll();

    public Scooter findById(int oId);

    public Scooter save(Scooter saveScooter);

    public Scooter deleteById(int oId);

    public List<Scooter> findByQuery(String jpqlName, Object... params);

}
