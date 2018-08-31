package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
}
