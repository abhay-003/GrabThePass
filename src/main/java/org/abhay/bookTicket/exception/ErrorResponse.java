package org.abhay.bookTicket.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private Date timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;
}
