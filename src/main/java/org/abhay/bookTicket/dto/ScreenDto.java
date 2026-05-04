package org.abhay.bookTicket.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDto {
	private Long id;
	private String name;
	private Integer totalseat;
	private LocalDateTime bookingTime;
	private TheaterDto theator;

}
