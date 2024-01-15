package raf.sk.drugiprojekat.rezervacioniservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.ScheduledTraining;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduledTrainingRepository extends JpaRepository<ScheduledTraining, Long> {
    Optional<ScheduledTraining> findScheduledTrainingById(Long id);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individualAndReservedSpotsLessThan(String offer_gym_name, String offer_training_name, Boolean offer_training_individual, Long reservedSpots);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByTimeSlot(LocalDateTime timeSlot);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByTimeSlotBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_gym_name(String offer_gym_name);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_training_nameAndOffer_training_individual(String offer_training_name, Boolean offer_training_individual);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individual(String offer_gym_name, String offer_training_name, Boolean offer_training_individual);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_training_individual(Boolean offer_training_individual);
    Optional<List<ScheduledTraining>> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_individual(String offer_gym_name, Boolean offer_training_individual);
}
