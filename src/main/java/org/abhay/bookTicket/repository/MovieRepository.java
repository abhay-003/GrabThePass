package org.abhay.bookTicket.repository;

import java.util.List;

import org.abhay.bookTicket.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	List<Movie> findByLanguage(String language);
	List<Movie> findByGenre(String genre);
	List<Movie> findByTitle(String title);

}
