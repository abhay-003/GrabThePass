package org.abhay.bookTicket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.abhay.bookTicket.Entity.Movie;
import org.abhay.bookTicket.dto.MovieDto;
import org.abhay.bookTicket.exception.ResourceNotFoundException;
import org.abhay.bookTicket.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movierepo;
	
	public MovieDto createMovie(MovieDto moviedto) {
		Movie movie = mapToEntity(moviedto);
		Movie saveMovie=movierepo.save(movie);
		return mapToMovieDto(saveMovie);
	}
	
	
	public MovieDto getMovieById(Long id) {
		Movie movie = movierepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found"));
		return mapToMovieDto(movie);
	}
	

	public List<MovieDto> getMovieByLanguage(String language) {
		List<Movie> movie = movierepo.findByLanguage(language);
		return movie.stream().map(this::mapToMovieDto).collect(Collectors.toList());
	}

	
	public List<MovieDto> getAllMovie(){
		List<Movie> movies = movierepo.findAll();
		return movies.stream().map(this::mapToMovieDto).collect(Collectors.toList());
	}
	
	
	public List<MovieDto> getMovieByGenere(String genere){
		List<Movie> movies = movierepo.findByGenre(genere);
		return movies.stream().map(this::mapToMovieDto).collect(Collectors.toList());
	}
	
	
	public List<MovieDto> getMovieByTitle(String title){
		List<Movie> movies = movierepo.findByTitle(title);
		return movies.stream().map(this::mapToMovieDto).collect(Collectors.toList());
	}
	
	public MovieDto updateMovie(Long id, MovieDto moviedto) {
		Movie movie = movierepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found with id:"+ id));
		movie.setTitle(moviedto.getTitle());
		movie.setDescription(moviedto.getDescription());
		movie.setGenre(moviedto.getGenre());
		movie.setLanguage(moviedto.getLanguage());
		movie.setPosterUrl(moviedto.getUrl());
		movie.setDuration(moviedto.getDuration());
		movie.setReleaseDate(moviedto.getReleaseDate());
		
		
		Movie updatedMovie=  movierepo.save(movie);
		return mapToMovieDto(updatedMovie);
	}
	
	
	public void deleteMovie(Long id) {
		Movie movie = movierepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Movie not found with id:"+id));
		movierepo.delete(movie);
		
	}
	
	
	public Movie mapToEntity(MovieDto moviedto) {
		
		Movie movie = new Movie();
		movie.setTitle(moviedto.getTitle());
		movie.setDescription(moviedto.getDescription());
		movie.setGenre(moviedto.getGenre());
		movie.setLanguage(moviedto.getLanguage());
		movie.setPosterUrl(moviedto.getUrl());
		movie.setDuration(moviedto.getDuration());
		movie.setReleaseDate(moviedto.getReleaseDate());
		return movie;
	}
	
	public MovieDto mapToMovieDto(Movie movie) {
		MovieDto movieDto = new MovieDto();
		movieDto.setId(movie.getId());
		movieDto.setTitle(movie.getTitle());
		movieDto.setDescription(movie.getDescription());
		movieDto.setDuration(movie.getDuration());
		movieDto.setReleaseDate(movie.getReleaseDate());
		movieDto.setGenre(movie.getGenre());
		movieDto.setLanguage(movie.getLanguage());
		movieDto.setUrl(movie.getPosterUrl());
		return movieDto;
		
	}

}
