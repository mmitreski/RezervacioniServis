package raf.sk.drugiprojekat.rezervacioniservis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationById(Long id);
    Optional<List<Reservation>> findAllReservationsByClientId(Long clientId);
    Optional<List<Reservation>> findAllReservationsByScheduledTraining_id(Long scheduledTraining_id);
    Optional<List<Reservation>> findAllReservationsByScheduledTraining_offer_gym_nameAndScheduledTraining_offer_training_nameAndScheduledTraining_offer_training_individual(String scheduledTraining_offer_gym_name, String scheduledTraining_offer_training_name, Boolean scheduledTraining_offer_training_individual);
}
