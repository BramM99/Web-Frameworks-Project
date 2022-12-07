package app.repositories;

import app.models.User;

import java.util.List;

public interface UserRepository {
    List<User> user = null;

    List<User> findAll();

    User findByMail(String email);

    User findById(long oId);

    User save(User saveUser);

    User deleteById(int oId);

}
