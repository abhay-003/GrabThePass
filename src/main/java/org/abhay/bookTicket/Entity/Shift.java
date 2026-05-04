package org.abhay.bookTicket.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	@ManyToOne
	@JoinColumn(name="movie_id", nullable = false)
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name="screen_id", nullable = false)
	private Screen screen;
	
	@OneToMany(mappedBy="shift", cascade = CascadeType.ALL)
	private List<ShiftSeat> shiftSeat;
	
	@OneToOne(mappedBy = "shift", cascade = CascadeType.ALL)
	private List<Booking> bookings;
}
