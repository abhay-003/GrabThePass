package org.abhay.bookTicket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.abhay.bookTicket.Entity.Movie;
import org.abhay.bookTicket.Entity.Screen;
import org.abhay.bookTicket.Entity.Shift;
import org.abhay.bookTicket.Entity.ShiftSeat;
import org.abhay.bookTicket.dto.MovieDto;
import org.abhay.bookTicket.dto.ScreenDto;
import org.abhay.bookTicket.dto.SeatDto;
import org.abhay.bookTicket.dto.ShiftDto;
import org.abhay.bookTicket.dto.ShiftSeatDto;
import org.abhay.bookTicket.dto.TheaterDto;
import org.abhay.bookTicket.exception.ResourceNotFoundException;
import org.abhay.bookTicket.repository.MovieRepository;
import org.abhay.bookTicket.repository.ScreenRepository;
import org.abhay.bookTicket.repository.ShiftRepository;
import org.abhay.bookTicket.repository.ShiftSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {
	
	@Autowired
	private MovieRepository movierepo;
	
	@Autowired
	private ScreenRepository screenrepo;
	
	@Autowired
	private ShiftRepository shiftrepo;
	
	@Autowired
	private ShiftSeatRepository shiftseatrepo;
	
	public ShiftDto createShift(ShiftDto shiftDto) {
		Shift shift= new Shift();
		
		
		Movie movie = movierepo.findById(shiftDto.getMovie().getId()).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found"));

	
//		Screen sreen = movierepo.findById(shiftDto.getScreen().getId()).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found"));

		Screen screen = screenrepo.findById(shiftDto.getScreen().getId()).orElseThrow(()-> new ResourceNotFoundException("Screen Not Found"));
		
		shift.setMovie(movie);
		shift.setScreen(screen);
		shift.setStartTime(shiftDto.getStartTime());
		shift.setEndTime(shiftDto.getEndTime());
		
		Shift savedShift= shiftrepo.save(shift);
		
		List<ShiftSeat> availableSeat= shiftseatrepo.findByShiftIdAndStatus(savedShift.getId(), "AVAILABLE");
		
		return mapToShiftDto(savedShift, availableSeat);
		}
	
	
	public ShiftDto getShiftById(Long id) {
		Shift shift = shiftrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Shift Not Found"));
		
		List<ShiftSeat> seats = shiftseatrepo.findByShiftIdAndStatus(shift.getId(), "AVAILABLE");
		
		return mapToShiftDto(shift, seats);
	}
	
	
	public List<ShiftDto> getAllShift(){
		List<Shift> shifts= shiftrepo.findAll();
		 return shifts.stream().map(shift-> {
			 List<ShiftSeat> availableSeats= shiftseatrepo.findByShiftIdAndStatus(shift.getId(), "AVAILABLE");
			 return mapToShiftDto(shift, availableSeats);
		 }).collect(Collectors.toList());
	}
	
	
	public List<ShiftDto> getShiftByMovie(Long movieId){
		List<Shift> shifts = shiftrepo.findByMovieId(movieId);
		return shifts.stream().map(shift->{
			List<ShiftSeat> availableSeats= shiftseatrepo.findByShiftIdAndStatus(shift.getId(), "AVAILABLE");
			return mapToShiftDto(shift, availableSeats);
		}).collect(Collectors.toList());
	}
	
	
	public List<ShiftDto> getShiftByMovieIdAndCity(Long movieId, String city){
		List<Shift> shifts = shiftrepo.findByMovie_IdAndScreen_Theater_City(movieId, city); 
		return shifts.stream().map(shift-> {
			List<ShiftSeat> availableSeats= shiftseatrepo.findByShiftIdAndStatus(shift.getId(), "AVAILABLE");
			return mapToShiftDto(shift, availableSeats);
		}).collect(Collectors.toList());
		
	}
	
	
	public List<ShiftDto> getShiftByDateRange(LocalDateTime startDate, LocalDateTime endDate){
		List<Shift> shifts = shiftrepo.findByStartTimeBetween(startDate, endDate);
		
		return shifts.stream().map(shift->{
			List<ShiftSeat> availableSeats= shiftseatrepo.findByShiftIdAndStatus(shift.getId(), "AVAILABLE");
			return mapToShiftDto(shift, availableSeats);
		}).toList();
	}
	public ShiftDto mapToShiftDto(Shift shift, List<ShiftSeat> availaSeats) {
		ShiftDto shiftDto = new ShiftDto();
		shiftDto.setId(shift.getId());
		shiftDto.setEndTime(shift.getEndTime());
		shiftDto.setStartTime(shift.getStartTime());
		
		
		MovieDto movie = new MovieDto();
		movie.setId(shift.getMovie().getId());
		movie.setDescription(shift.getMovie().getDescription());
		movie.setTitle(shift.getMovie().getTitle());
		movie.setDuration(shift.getMovie().getDuration());
		movie.setLanguage(shift.getMovie().getLanguage());
		movie.setGenre(shift.getMovie().getGenre());
		movie.setReleaseDate(shift.getMovie().getReleaseDate());
		movie.setUrl(shift.getMovie().getPosterUrl());
		shiftDto.setMovie(movie);
		
		TheaterDto theater = new TheaterDto();
		theater.setId(shift.getScreen().getTheater().getId());
		theater.setName(shift.getScreen().getTheater().getName());
		theater.setAddress(shift.getScreen().getTheater().getAddress());
		theater.setCity(shift.getScreen().getTheater().getCity());
		theater.setTotalScreen(shift.getScreen().getTheater().getTotalScreen());
		
		
		ScreenDto screen = new ScreenDto();
		screen.setId(shift.getScreen().getId());
		screen.setName(shift.getScreen().getName());
		screen.setTotalseat(shift.getScreen().getTotalSeats());

		screen.setTheator(theater);
		
		List<ShiftSeatDto> seatDtos= availaSeats.stream().map(seat->{
			ShiftSeatDto shiftseatDto = new ShiftSeatDto();
			shiftseatDto.setId(seat.getId());
			shiftseatDto.setStatus(seat.getStatus());
			shiftseatDto.setPrice(seat.getPrice());
			
			SeatDto seatdto= new SeatDto();
			seatdto.setId(seat.getSeat().getId());
			seatdto.setSeatNumber(seat.getSeat().getSeatNumber());
			seatdto.setSeatNumber(seat.getSeat().getSeatNumber());
			seatdto.setBasePrice(seat.getSeat().getBasePrice());

			shiftseatDto.setSeat(seatdto);
			return shiftseatDto;
			
		}).collect(Collectors.toList());
		shiftDto.setAvailableSeats(seatDtos);
		return shiftDto;
		
	}

}
