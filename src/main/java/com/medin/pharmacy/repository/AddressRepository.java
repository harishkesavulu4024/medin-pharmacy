package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
