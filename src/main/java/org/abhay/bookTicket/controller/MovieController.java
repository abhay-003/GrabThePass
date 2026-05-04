package org.abhay.bookTicket.controller;

import java.util.List;

import org.abhay.bookTicket.dto.MovieDto;
import org.abhay.bookTicket.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	
	@PostMapping
	public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody MovieDto movieDto){
		return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
	}

	@GetMapping("/movie/{id}")
	public ResponseEntity<MovieDto> getMovieById(@PathVariable Long Id){
		return new ResponseEntity<>(movieService.getMovieById(Id), HttpStatus.OK);
	}


	@GetMapping("/movie/title")
	public ResponseEntity<List<MovieDto>> getMovieByTitle(@RequestParam String title){
		return new ResponseEntity<>(movieService.getMovieByTitle(title), HttpStatus.OK);
	}
	
	@GetMapping("/movie/language")
	public ResponseEntity<List<MovieDto>> getMovieByLanguage(@RequestParam String language){
		return new ResponseEntity<>(movieService.getMovieByLanguage(language), HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<List<MovieDto>> getAllMovies(){
		return  ResponseEntity.ok(movieService.getAllMovie());
	}
	
	@PutMapping("/movie/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable Long id){
		movieService.deleteMovie(id);
		return new ResponseEntity<>("Movie deleted SUCCESSFULLY", HttpStatus.OK);
	}

}
