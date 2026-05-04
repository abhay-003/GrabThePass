package org.abhay.bookTicket.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shift_seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftSeat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="shift_id", nullable = false)
	private Shift shift;
	
	@ManyToOne
	@JoinColumn(name="seat_id", nullable = false)
	private Seat seat;
	
	@Column(nullable = false)
	private String status;
	
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="booking_id")
	private Booking booking;

}
