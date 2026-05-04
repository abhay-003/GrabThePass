package org.abhay.bookTicket.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDto {
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private MovieDto movie;
	private ScreenDto screen;
	private List<ShiftSeatDto> availableSeats;

}
