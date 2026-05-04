package org.abhay.bookTicket.repository;

import java.util.List;
import java.util.Optional;

import org.abhay.bookTicket.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByUserId(Long userId);
	
	Optional<Booking> findByBookingNumber(String bookingNumber);
	
	List<Booking> findByShiftId(Long id);

}
