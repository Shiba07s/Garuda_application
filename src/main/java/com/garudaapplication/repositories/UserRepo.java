package com.garudaapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.garudaapplication.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
