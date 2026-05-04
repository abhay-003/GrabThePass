package org.abhay.bookTicket.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookinngRequestDto {
	private Long userId;
	protected Long shiftId;
	private List<Long> seatsId;
	private String paymentMethod;

}
