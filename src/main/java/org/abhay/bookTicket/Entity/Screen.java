package org.abhay.bookTicket.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="screens")
@Data // TO generate getter setters for the below mentioned field at the runtime.
@NoArgsConstructor // TO generate default no_argument constructor at the runtime.
@AllArgsConstructor // To generate default argumented constructor at the runtime.
public class Screen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer totalSeats;
	
	@ManyToOne
	@JoinColumn(name = "theater_id", nullable = false)
	private Theater theater;
	
	@OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
	private List<Shift> shifts;
	
	@OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
	private List<Seat> seat; 
}
