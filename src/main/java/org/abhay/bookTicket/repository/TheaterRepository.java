package org.abhay.bookTicket.repository;

import java.util.List;

import org.abhay.bookTicket.Entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long>{
//	List<Theater> findByShiftId(Long shiftId);
	List<Theater> findByCity(String city);
	

}
