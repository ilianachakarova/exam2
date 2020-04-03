package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
   Optional<Passenger>findByEmail(String email);
   @Query("select p from Passenger as p order by p.tickets.size desc, p.email asc")
   List<Passenger>findAllByTicketsCount();
}
