package org.abhay.bookTicket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.abhay.bookTicket.Entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>{
		List<Shift> findByMovieId(Long movieId);
		List<Shift> findByScreenId(Long screenId);
		List<Shift> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
		List<Shift> findByMovie_IdAndScreen_Theater_City(Long movieId, String city);
		
}
