package org.abhay.bookTicket.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
	private Long id;
	private String bookingNumber;
	private LocalDateTime bookingTime;
	private UserDto user;
	private ShiftDto shift;
	private String status;
	private double totalAmount;
	private List<ShiftSeatDto> seats;
	private PaymentDto payment;
//	private 

}
