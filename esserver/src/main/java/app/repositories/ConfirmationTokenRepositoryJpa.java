package app.repositories;

import app.models.ConfirmationToken;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


@Repository
@Primary
@Transactional
public class ConfirmationTokenRepositoryJpa implements ConfirmationTokenRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return entityManager.merge(confirmationToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        TypedQuery<ConfirmationToken> query =
                this.entityManager.createQuery(
                        "select c from ConfirmationToken c where c.confirmationToken =:search", ConfirmationToken.class);
        query.setParameter("search", confirmationToken);
        return query.getSingleResult();
    }
}
