package app.repositories;

import app.models.Scooter;
import app.models.Trip;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Primary
@Repository
@Transactional
public class TripsRepositoryJpa implements TripRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Trip> findAll() {
        TypedQuery<Trip> query =
                this.entityManager.createQuery(
                        "select t from Trip t", Trip.class);
        return query.getResultList();
    }

    @Override
    public Trip findById(int oId) {
        return entityManager.find(Trip.class, oId);
    }

    @Override
    public Trip save(Trip saveTrip) {
        return entityManager.merge(saveTrip);
    }

    @Override
    public Trip deleteById(int oId) {
        Trip trip = entityManager.find(Trip.class, oId);
        entityManager.remove(trip);
        return trip;
    }

    @Override
    public List<Trip> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Trip> Trip_find_current_from_scooter = entityManager.createNamedQuery(
                "Trip_find_current_from_scooter",
                Trip.class);
        return Trip_find_current_from_scooter.getResultList();
    }
}
