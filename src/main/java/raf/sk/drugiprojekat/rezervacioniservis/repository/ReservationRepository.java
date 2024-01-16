package raf.sk.drugiprojekat.rezervacioniservis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationById(Long id);
    List<Reservation> findAllReservationsByClientId(Long clientId);
    List<Reservation> findAllReservationsByScheduledTraining_id(Long scheduledTraining_id);
}
