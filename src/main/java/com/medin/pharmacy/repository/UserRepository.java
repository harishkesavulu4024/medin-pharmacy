package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String userName);

}
