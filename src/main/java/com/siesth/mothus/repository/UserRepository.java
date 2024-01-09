package com.siesth.mothus.repository;

import com.siesth.mothus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);
    boolean existsUserByMail(String mail);
}
