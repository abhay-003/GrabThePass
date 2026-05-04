package org.abhay.bookTicket.Entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="movies")
@Data // TO generate getter setters for the below mentioned field at the runtime.
@NoArgsConstructor // TO generate default no_argument constructor at the runtime.
@AllArgsConstructor // To generate default argumented constructor at the runtime.
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	private String description;
	private String language;
	private String genre;
	private Integer duration;
	private String releaseDate;
	private String posterUrl;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Shift> shift;
	
}
