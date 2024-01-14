package com.siesth.mothus.repository;

import com.siesth.mothus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the User model
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by his username
     * @param username The username of the user
     * @return The user with the given username
     */
    User findUserByUsername(String username);

    /**
     * Find a user by his mail
     * @param mail The mail of the user
     * @return The user with the given mail
     */
    User findUserByMail(String mail);

    /**
     * Find if a user exists by his username
     * @param username The username of the user
     * @return True if the user exists, false otherwise
     */
    boolean existsUserByUsername(String username);

    /**
     * Find if a user exists by his mail
     * @param mail The mail of the user
     * @return True if the user exists, false otherwise
     */
    boolean existsUserByMail(String mail);
}
