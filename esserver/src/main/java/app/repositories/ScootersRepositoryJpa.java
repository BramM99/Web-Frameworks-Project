package app.repositories;

import app.models.Scooter;
import org.hibernate.annotations.NamedQuery;
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
public class ScootersRepositoryJpa implements ScooterRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Scooter> findAll() {
        TypedQuery<Scooter> query =
                this.entityManager.createQuery(
                        "select s from Scooter s", Scooter.class);
        return query.getResultList();
    }

    @Override
    public Scooter findById(int oId) {
        return entityManager.find(Scooter.class, oId);
    }

    @Override
    public Scooter save(Scooter saveScooter) {
        return entityManager.merge(saveScooter);
    }

    @Override
    public Scooter deleteById(int oId) {
        Scooter scooter = entityManager.find(Scooter.class, oId);
        entityManager.remove(scooter);
        return scooter;
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        if (jpqlName.equals("Scooter_find_by_status")) {
            TypedQuery<Scooter> Scooter_find_by_status = entityManager.createNamedQuery(
                    "Scooter_find_by_status",
                    Scooter.class);
            Scooter_find_by_status.setParameter(1, params[0].toString());
            return Scooter_find_by_status.getResultList();
        }

        if (jpqlName.equals("Scooter_find_by_battery")) {
            TypedQuery<Scooter> Scooter_find_by_battery = entityManager.createNamedQuery(
                    "Scooter_find_by_battery",
                    Scooter.class);
            Scooter_find_by_battery.setParameter(1, Integer.parseInt(params[0].toString()));
            return Scooter_find_by_battery.getResultList();
        }
        return null;
    }
}
