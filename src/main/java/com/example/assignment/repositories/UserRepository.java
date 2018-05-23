package com.example.assignment.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.assignment.models.User;
public interface UserRepository extends CrudRepository<User, Integer> {
	
}
