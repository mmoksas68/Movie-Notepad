package com.obss.userProject.dao;

import com.obss.userProject.classes.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<MyUser, Long> {
    MyUser findDistinctFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Object> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
