package org.abhay.bookTicket.controller;

import java.util.List;

import org.abhay.bookTicket.dto.BookingDto;
import org.abhay.bookTicket.dto.BookinngRequestDto;
import org.abhay.bookTicket.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookinngRequestDto bookingrequest){
		return new ResponseEntity<>(bookingService.createBooking(bookingrequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/booking/{id}")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id){
		return new ResponseEntity<>(bookingService.getBookingByBookingId(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long id){
		return new ResponseEntity<>(bookingService.cancelBooking(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<BookingDto>> getBookingByUserId(@PathVariable Long id){
		return new ResponseEntity<>(bookingService.getBookingByUserId(id), HttpStatus.OK);
	}
	
}
