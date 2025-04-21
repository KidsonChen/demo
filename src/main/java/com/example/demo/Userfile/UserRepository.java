package com.example.demo.Userfile;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUseremail(String useremail);
}