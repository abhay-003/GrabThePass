package org.abhay.bookTicket.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payments")
@Data // TO generate getter setters for the below mentioned field at the runtime.
@NoArgsConstructor // TO generate default no_argument constructor at the runtime.
@AllArgsConstructor // To generate default argumented constructor at the runtime.
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String transactionId;
	private Double amount;
	private LocalDateTime paymentTime;
	private String paymentMethod;
	private String status;
	
	@OneToOne(mappedBy = "payment")
	private Booking booking;
	
}
