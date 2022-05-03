package com.apitest.demo.repository;

import com.apitest.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepositoryCustom extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String name);

}

