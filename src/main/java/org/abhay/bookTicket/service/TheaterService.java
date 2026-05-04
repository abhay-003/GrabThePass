package org.abhay.bookTicket.service;

import java.util.List;

import org.abhay.bookTicket.Entity.Theater;
import org.abhay.bookTicket.dto.TheaterDto;
import org.abhay.bookTicket.exception.ResourceNotFoundException;
import org.abhay.bookTicket.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {
	
	@Autowired
	private TheaterRepository theaterRepo;
	
	public TheaterDto createTheater(TheaterDto theaterDto) {
		Theater theater = mapToEntity(theaterDto);
		Theater saveTheater= theaterRepo.save(theater);
		return mapToTheaterDto(saveTheater);
		
	}

	public TheaterDto mapToTheaterDto(Theater theater) {
		// TODO Auto-generated method stub
		TheaterDto theaterDto= new TheaterDto();
		theaterDto.setId(theater.getId());
		theaterDto.setAddress(theater.getAddress());
		theaterDto.setCity(theater.getCity());
		theaterDto.setName(theater.getName());
		theaterDto.setTotalScreen(theater.getTotalScreen());
		
		return theaterDto;
	}

	public Theater mapToEntity(TheaterDto theaterDto) {
		// TODO Auto-generated method stub
		Theater theater = new Theater();
		theater.setAddress(theaterDto.getAddress());
		theater.setCity(theaterDto.getCity());
		theater.setName(theaterDto.getName());
		theater.setTotalScreen(theaterDto.getTotalScreen());
		
		return theater;
	}
	
	public TheaterDto getTheaterById(Long id) {
		Theater theaters= theaterRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Theater Not Found By id:"+id));
		return mapToTheaterDto(theaters);
	}
	
	public List<TheaterDto> getAllTheater(){
		List<Theater> theaters = theaterRepo.findAll();
//		return List<TheaterDto> mapToTheaterDto(theaters);
		return theaters.stream().map(this::mapToTheaterDto).toList();
	}
	
	public List<TheaterDto> getAllTheaterByCity(String city){
		List<Theater> theaters= theaterRepo.findByCity(city);
		return theaters.stream().map(this::mapToTheaterDto).toList();

	}
	
	// delete Theater 
	public void deleteTheater(Long id) {
		Theater theater = theaterRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Theater Not Found By id:"+id));
		theaterRepo.delete(theater);
	}
	
	
	//Update Theater
	
	public TheaterDto updateTheater(Long id, TheaterDto theaterDto) {
		Theater theater = theaterRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Theater Not Found By id:"+id));
		theater.setAddress(theaterDto.getAddress());
		theater.setCity(theaterDto.getCity());
		theater.setTotalScreen(theaterDto.getTotalScreen());
		theater.setName(theaterDto.getName());
		Theater updatedTheater= theaterRepo.save(theater);
		return mapToTheaterDto(updatedTheater);
	}

}
