package app.repositories;

import app.models.ConfirmationToken;

public interface ConfirmationTokenRepository {
    ConfirmationToken save(ConfirmationToken confirmationToken);

    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
