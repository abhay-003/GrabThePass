package org.abhay.bookTicket.repository;

import java.util.Optional;

import org.abhay.bookTicket.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	Optional<User> findByPhoneNumber(String number);
}
