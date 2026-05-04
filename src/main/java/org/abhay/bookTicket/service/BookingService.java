package org.abhay.bookTicket.service;

//import java.beans.Beans;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
//import java.util.stream.Stream;

import org.abhay.bookTicket.Entity.Booking;
import org.abhay.bookTicket.Entity.Payment;
import org.abhay.bookTicket.Entity.Shift;
import org.abhay.bookTicket.Entity.ShiftSeat;
import org.abhay.bookTicket.Entity.User;
import org.abhay.bookTicket.dto.BookingDto;
import org.abhay.bookTicket.dto.BookinngRequestDto;
import org.abhay.bookTicket.dto.MovieDto;
import org.abhay.bookTicket.dto.PaymentDto;
import org.abhay.bookTicket.dto.ScreenDto;
import org.abhay.bookTicket.dto.SeatDto;
import org.abhay.bookTicket.dto.ShiftDto;
import org.abhay.bookTicket.dto.ShiftSeatDto;
import org.abhay.bookTicket.dto.TheaterDto;
import org.abhay.bookTicket.dto.UserDto;
import org.abhay.bookTicket.exception.ResourceNotFoundException;
import org.abhay.bookTicket.exception.SeatUnavailableException;
import org.abhay.bookTicket.repository.BookingRepository;
import org.abhay.bookTicket.repository.ShiftRepository;
import org.abhay.bookTicket.repository.ShiftSeatRepository;
import org.abhay.bookTicket.repository.UserRepository;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ShiftRepository shiftRepo;
	
	@Autowired
	private ShiftSeatRepository shiftseatrepo;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	
	
	
	@Transactional
	public BookingDto createBooking(BookinngRequestDto bookingRequest) {
		User user= userRepo.findById(bookingRequest.getUserId())
				.orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		
		Shift shift = shiftRepo.findById(bookingRequest.getShiftId()).orElseThrow(()-> new SeatUnavailableException("Seat Not Available"));
		
				
		List<ShiftSeat> selectedseat =  shiftseatrepo.findAllById(bookingRequest.getSeatsId());
		for(ShiftSeat seat : selectedseat) {
			if (!"AVAILABLE".equals(seat.getStatus())) {
				throw new SeatUnavailableException("Seat"+ seat.getSeat().getSeatNumber()+ " Not Available");
			}
			seat.setStatus("LOCKES");
			
		}
		shiftseatrepo.saveAll(selectedseat);
		
		Double totalAmount= selectedseat.stream().mapToDouble(ShiftSeat::getPrice).sum();
		
		
		
		// Setting Payment in this way because not right now service is not getting payment details from UI or any payment gateway.
		Payment payment =new Payment();
		payment.setAmount(totalAmount);
		payment.setPaymentTime(LocalDateTime.now());
		payment.setPaymentMethod(bookingRequest.getPaymentMethod());
		payment.setStatus("SUCCESS");
		payment.setTransactionId(UUID.randomUUID().toString());
		
		
		// Placing Booking
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShift(shift);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus("CONFIRM");
		booking.setTotalAmount(totalAmount);
		booking.setBookingNumber(UUID.randomUUID().toString());
		booking.setPayment(payment);
		
		Booking saveBooking= bookingRepo.save(booking);
		
		selectedseat.forEach(seat -> {
			seat.setStatus("BOOKED");
			seat.setBooking(saveBooking);
		});
		shiftseatrepo.saveAll(selectedseat);
		return mapToBookingDto(saveBooking, selectedseat);
	}
	
	public BookingDto mapToBookingDto(Booking booking, List<ShiftSeat> seats) {
		BookingDto bookingdto = new BookingDto();
		bookingdto.setId(booking.getId());
		bookingdto.setBookingNumber(booking.getBookingNumber());
		bookingdto.setBookingTime(booking.getBookingTime());
		bookingdto.setStatus(booking.getStatus());
		bookingdto.setTotalAmount(booking.getTotalAmount());
		
		UserDto userdto = new UserDto();
//		BeanUtils.copyProperties(booking.getUser(), userdto); //BeanUtils is used to copy the the exact object to another object.
//		below used in old traditional way
		userdto.setId(booking.getUser().getId());		
		userdto.setName(booking.getUser().getName());		
		userdto.setEmail(booking.getUser().getEmail());		
		userdto.setPhoneNumber(booking.getUser().getPhoneNumber());		
		
		bookingdto.setUser(userdto);
		
		ShiftDto shiftDto = new ShiftDto();
//		BeanUtils.copyProperties(booking.getShift(), shiftDto);
		
		shiftDto.setId(booking.getShift().getId());
		shiftDto.setStartTime(booking.getShift().getStartTime());
		shiftDto.setEndTime(booking.getShift().getEndTime());
		
		MovieDto movieDto = new MovieDto();
		movieDto.setId(booking.getShift().getMovie().getId());
		movieDto.setTitle(booking.getShift().getMovie().getTitle());
		movieDto.setDescription(booking.getShift().getMovie().getDescription());
		movieDto.setLanguage(booking.getShift().getMovie().getLanguage());
		movieDto.setDuration(booking.getShift().getMovie().getDuration());
		movieDto.setGenre(booking.getShift().getMovie().getGenre());
		movieDto.setReleaseDate(booking.getShift().getMovie().getReleaseDate());
		movieDto.setUrl(booking.getShift().getMovie().getPosterUrl());
		
		shiftDto.setMovie(movieDto);
		
		ScreenDto screenDto = new ScreenDto();
		screenDto.setId(booking.getShift().getScreen().getId());
		screenDto.setName(booking.getShift().getScreen().getName());
		screenDto.setTotalseat(booking.getShift().getScreen().getTotalSeats());

		TheaterDto theaterDto = new TheaterDto();
		theaterDto.setId(booking.getShift().getScreen().getTheater().getId());
		theaterDto.setName(booking.getShift().getScreen().getTheater().getName());
		theaterDto.setAddress(booking.getShift().getScreen().getTheater().getAddress());
		theaterDto.setCity(booking.getShift().getScreen().getTheater().getCity());
		theaterDto.setTotalScreen(booking.getShift().getScreen().getTheater().getTotalScreen());
		//		bookingdto.set
		
		screenDto.setTheator(theaterDto);
		shiftDto.setScreen(screenDto);
		bookingdto.setShift(shiftDto);
		
//		seat.stream().map(seat-> )
		List<ShiftSeatDto> seatDtos= seats.stream().map(seat->{
			ShiftSeatDto seatDto= new ShiftSeatDto();
			seatDto.setId(seat.getId());
			seatDto.setStatus(seat.getStatus());
			seatDto.setPrice(seat.getPrice());
			
			SeatDto baseseaDto= new SeatDto();
			baseseaDto.setId(seat.getSeat().getId());
			baseseaDto.setSeatNumber(seat.getSeat().getSeatNumber());
			baseseaDto.setBasePrice(seat.getSeat().getBasePrice());
			baseseaDto.setSeatType(seat.getSeat().getSeatType());
			seatDto.setSeat(baseseaDto);
			return seatDto;
		})
		.collect(Collectors.toList());
		
		bookingdto.setSeats(seatDtos);
		
		if(booking.getPayment()!=null) {
			PaymentDto paymentDto= new PaymentDto();
			paymentDto.setId(booking.getPayment().getId());
			paymentDto.setAmount(booking.getPayment().getAmount());
			paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
			paymentDto.setPaymentTime(booking.getPayment().getPaymentTime());
			paymentDto.setStatus(booking.getPayment().getStatus());
			paymentDto.setTransactionId(booking.getPayment().getTransactionId());
			bookingdto.setPayment(paymentDto);
		}
		return bookingdto;		
	}
	
	
	public BookingDto getBookingByBookingId(Long id) {
		Booking booking = bookingRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Booking Not Found")); 
		
		List<ShiftSeat> seats=  shiftseatrepo.findAll()
		.stream()
		.filter(seat-> seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId())).collect(Collectors.toList());
		
		return mapToBookingDto(booking, seats);
	}
	
	
	
	public BookingDto getBookingByNmber(String bookingNumber) {
		Booking booking = bookingRepo.findByBookingNumber(bookingNumber).orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
		
		List<ShiftSeat> seats = shiftseatrepo.findAll()
				.stream()
				.filter(seat-> seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId())).collect(Collectors.toList());
		
		return mapToBookingDto(booking, seats);
	}
	
	
	public List<BookingDto> getBookingByUserId(Long id) {
		List<Booking> bookings=bookingRepo.findByUserId(id);
		
		return bookings.stream().map(booking -> {
			List<ShiftSeat> seats = shiftseatrepo.findAll()
					.stream()
					.filter(seat -> seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId())).collect(Collectors.toList());
			return mapToBookingDto(booking, seats);
		}).collect(Collectors.toList());
	}
	
	
	@Transactional
	public BookingDto cancelBooking(Long id) {
		Booking booking = bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
		
		booking.setStatus("CANCEL");
		
		List<ShiftSeat> seats=  shiftseatrepo.findByBookingId(id);
//				findAll().stream()
//		.filter(seat -> seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId())).collect(Collectors.toList());
		
		seats.forEach(seat->{
			seat.setStatus("AVAILABLE");
			seat.setBooking(null);
		});
		
		if(booking.getPayment()!=null) {
			booking.getPayment().setStatus("REFUNDED");
		}
		
		Booking updateBooking = bookingRepo.save(booking);
		shiftseatrepo.saveAll(seats);
		return mapToBookingDto(updateBooking, seats);
		
	}
}









