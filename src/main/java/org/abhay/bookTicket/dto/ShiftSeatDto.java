package org.abhay.bookTicket.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftSeatDto {
	private Long id;
	private String status;
	private double price;
	private SeatDto seat;

}
