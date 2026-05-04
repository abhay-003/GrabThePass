package org.abhay.bookTicket.repository;

import java.util.Optional;

import org.abhay.bookTicket.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
		Optional<Payment> findByTransactionId(String transcationId);
}
