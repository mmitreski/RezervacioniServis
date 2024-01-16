package raf.sk.drugiprojekat.rezervacioniservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.ScheduledTraining;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduledTrainingRepository extends JpaRepository<ScheduledTraining, Long> {
    Optional<ScheduledTraining> findScheduledTrainingById(Long id);
    Optional<ScheduledTraining> findScheduledTrainingByOffer_idAndTimeSlot(Long offer_id, LocalDateTime timeSlot);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individualAndReservedSpotsLessThan(String offer_gym_name, String offer_training_name, Boolean offer_training_individual, Long reservedSpots);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_gym_name = ?1 AND offer_training_name = ?2 AND offer_training_individual = ?3 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(String offer_gym_name, String offer_training_name, Boolean offer_training_individual);
    @Query(value = "SELECT * FROM scheduled_training WHERE timeSlot = ?1 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByTimeSlotWhereReservedSpotsLessThanMax(LocalDateTime timeSlot);
    @Query(value = "SELECT * FROM scheduled_training WHERE timeSlot BETWEEN ?1 AND ?2 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByTimeSlotBetweenWhereReservedSpotsLessThanMax(LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_gym_name = ?1 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameWhereReservedSpotsLessThanMax(String offer_gym_name);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_training_name = ?1 AND offer_training_individual = ?2 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_training_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(String offer_training_name, Boolean offer_training_individual);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_training_individual = ?1 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_training_individualWhereReservedSpotsLessThanMax(Boolean offer_training_individual);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_gym_name = ?1 AND offer_training_individual = ?2 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(String offer_gym_name, Boolean offer_training_individual);
    @Query(value = "SELECT * FROM scheduled_training WHERE offer_gym_name = ?1 AND timeSlot BETWEEN ?2 AND ?3 AND reservedSpots < max", nativeQuery = true)
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndTimeSlotBetweenWhereReservedSpotsLessThanMax(String offer_gym_name, LocalDateTime startDate, LocalDateTime endDate);
    List<ScheduledTraining> findAllScheduledTrainingsByTimeSlot(LocalDateTime timeSlot);
    List<ScheduledTraining> findAllScheduledTrainingsByTimeSlotBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_name(String offer_gym_name);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_training_nameAndOffer_training_individual(String offer_training_name, Boolean offer_training_individual);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individual(String offer_gym_name, String offer_training_name, Boolean offer_training_individual);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_training_individual(Boolean offer_training_individual);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_individual(String offer_gym_name, Boolean offer_training_individual);
    List<ScheduledTraining> findAllScheduledTrainingsByOffer_gym_nameAndTimeSlotBetween(String offer_gym_name, LocalDateTime startDate, LocalDateTime endDate);
}
