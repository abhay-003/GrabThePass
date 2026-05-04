package org.abhay.bookTicket.repository;

import java.util.List;

import org.abhay.bookTicket.Entity.Payment;
import org.abhay.bookTicket.Entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long>{
		List<Screen> findByTheaterId(Long theaterId);
		
}
