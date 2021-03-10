package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.StoreSchedule;

@Repository
public interface StoreScheduleRepository extends JpaRepository<StoreSchedule, Long> {

}
