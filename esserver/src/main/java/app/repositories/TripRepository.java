package app.repositories;

import app.models.Trip;

import java.util.List;

public interface TripRepository {
    List<Trip> trips = null;

    public List<Trip> findAll();

    public Trip findById(int oId);

    public Trip save(Trip saveScooter);

    public Trip deleteById(int oId);

    public List<Trip> findByQuery(String jpqlName, Object... params);
}
