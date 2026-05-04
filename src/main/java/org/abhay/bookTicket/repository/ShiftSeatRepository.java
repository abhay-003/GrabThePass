package org.abhay.bookTicket.repository;

import java.util.List;

import org.abhay.bookTicket.Entity.ShiftSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftSeatRepository extends JpaRepository<ShiftSeat, Long>{
	List<ShiftSeat> findByShiftId(Long shiftId);
	List<ShiftSeat> findByShiftIdAndStatus(Long shiftId, String status);
	List<ShiftSeat> findByBookingId(Long bookingId);
	

}
