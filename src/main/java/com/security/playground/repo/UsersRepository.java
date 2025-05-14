package com.security.playground.repo;

import com.security.playground.model.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);
}
