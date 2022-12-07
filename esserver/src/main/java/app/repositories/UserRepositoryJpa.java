package app.repositories;

import app.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Primary
@Transactional
@Repository
public class UserRepositoryJpa implements UserRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long oId) {
       return entityManager.find(User.class,oId);
    }

    public User findByMail(String email){
        TypedQuery<User> userFindByEmail = entityManager.createNamedQuery(
                "User_find_by_email",
                User.class);
        userFindByEmail.setParameter(1, email);
        return userFindByEmail.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User save(User saveUser) {
        return entityManager.merge(saveUser);
    }

    @Override
    public User deleteById(int oId) {
        return null;
    }
}
