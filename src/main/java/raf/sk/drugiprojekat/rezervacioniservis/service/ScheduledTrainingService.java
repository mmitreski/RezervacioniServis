package raf.sk.drugiprojekat.rezervacioniservis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingUpdateDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledTrainingService {
    Page<ScheduledTrainingDto> findAll(Pageable pageable);
    ScheduledTrainingDto findById(Long id);
    ScheduledTrainingDto findByOfferIdTimeSlot(Long offerId, LocalDateTime timeSlot);
    List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividualAndReservedSpotsLessThan(String gymName, String trainingName, Boolean trainingIndividual, Long reservedSpots);
    List<ScheduledTrainingDto> findByTimeSlot(LocalDateTime timeSlot);
    List<ScheduledTrainingDto> findByTimeSlotBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<ScheduledTrainingDto> findByGymName(String gymName);
    List<ScheduledTrainingDto> findByTrainingNameAndTrainingIndividual(String trainingName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividual(String gymName, String trainingName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByTrainingIndividual(Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndTrainingIndividual(String gymName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndWeekday(String gymName, String dayOfWeek);
    List<ScheduledTrainingDto> findByGymNameAndTimeSlotBetween(String gymName, LocalDateTime startDate, LocalDateTime endDate);
    /*** FREE ***/
    List<ScheduledTrainingDto> findByTimeSlotFree(LocalDateTime timeSlot);
    List<ScheduledTrainingDto> findByTimeSlotBetweenFree(LocalDateTime startDate, LocalDateTime endDate);
    List<ScheduledTrainingDto> findByGymNameFree(String gymName);
    List<ScheduledTrainingDto> findByTrainingNameAndTrainingIndividualFree(String trainingName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividualFree(String gymName, String trainingName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByTrainingIndividualFree(Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndTrainingIndividualFree(String gymName, Boolean trainingIndividual);
    List<ScheduledTrainingDto> findByGymNameAndWeekdayFree(String gymName, String dayOfWeek);
    List<ScheduledTrainingDto> findByGymNameAndTimeSlotBetweenFree(String gymName, LocalDateTime startDate, LocalDateTime endDate);
    ScheduledTrainingDto create(ScheduledTrainingCreateDto scheduledTrainingCreateDto);
    ScheduledTrainingDto update(Long id, ScheduledTrainingUpdateDto scheduledTrainingUpdateDto);
    void deleteById(Long id);
}
