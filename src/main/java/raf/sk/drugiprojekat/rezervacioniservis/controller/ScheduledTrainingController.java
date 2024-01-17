package raf.sk.drugiprojekat.rezervacioniservis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import raf.sk.drugiprojekat.rezervacioniservis.dto.*;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.ScheduledTrainingService;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/scheduled")
public class ScheduledTrainingController {
    private ScheduledTrainingService scheduledTrainingService;

    public ScheduledTrainingController(ScheduledTrainingService scheduledTrainingService) {
        this.scheduledTrainingService = scheduledTrainingService;
    }

    @Operation(summary = "Get all gym")
    @Parameters({
            @Parameter(name = "page", description = "What page number you want", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "Number of items to return", in = ParameterIn.QUERY),
            @Parameter(name = "sort", in = ParameterIn.QUERY,
                    description = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<ScheduledTrainingDto>> getAllScheduledTrainings(Pageable pageable) {
        return new ResponseEntity<>(scheduledTrainingService.findAll(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get scheduled training by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<ScheduledTrainingDto> getScheduledById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(scheduledTrainingService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get scheduled training by offer id and time slot")
    @GetMapping(params = {"offerId", "timeSlot"})
    public ResponseEntity<ScheduledTrainingDto> getScheduledByOfferIdTimeSlot(@RequestParam("id") Long offerId, @RequestParam("timeSlot") LocalDateTime timeSlot) {
        return new ResponseEntity<>(scheduledTrainingService.findByOfferIdTimeSlot(offerId, timeSlot), HttpStatus.OK);
    }

    @Operation(summary = "Get scheduled trainings by gym name, training name, training individual and reserved spots less than")
    @GetMapping(params = {"gymName", "trainingName", "trainingIndividual", "reservedSpots"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTrainingNameTrainingIndividualReservedSpotsLessThan(@RequestParam("gymName") String gymName, @RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual, @RequestParam("reservedSpots") Long reservedSpots) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTrainingNameAndTrainingIndividualAndReservedSpotsLessThan(gymName, trainingName, trainingIndividual, reservedSpots), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled trainings by time slot")
    @GetMapping(params = {"timeSlot"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledTimeSlot(@RequestParam("timeSlot") LocalDateTime timeSlot) {
        return new ResponseEntity<>(scheduledTrainingService.findByTimeSlot(timeSlot), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training with time slot between")
    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledBetween(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return new ResponseEntity<>(scheduledTrainingService.findByTimeSlotBetween(startDate, endDate), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name")
    @GetMapping(params = {"gymName"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymName(@RequestParam("gymName") String gymName) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymName(gymName), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by training name and training individual")
    @GetMapping(params = {"trainingName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledTrainingNameTrainingIndividual(@RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByTrainingNameAndTrainingIndividual(trainingName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and training name and training individual")
    @GetMapping(params = {"gymName", "trainingName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTrainingNameTrainingIndividual(@RequestParam("gymName") String gymName, @RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTrainingNameAndTrainingIndividual(gymName, trainingName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by training individual")
    @GetMapping(params = {"trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllSchedulledTrainingIndividual(@RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByTrainingIndividual(trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and training individual")
    @GetMapping(params = {"gymName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTrainingIndividual(@RequestParam("gymName") String gymName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTrainingIndividual(gymName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and weekday")
    @GetMapping(params = {"gymName", "weekday"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameWeekday(@RequestParam("gymName") String gymName, @RequestParam("weekday") String weekday) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndWeekday(gymName, weekday), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and time slot between")
    @GetMapping(params = {"gymName", "startDate", "endDate"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTimeSlotBetween(@RequestParam("gymName") String gymName, @RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTimeSlotBetween(gymName, startDate, endDate), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled trainings by time slot")
    @GetMapping(value = "/free", params = {"timeSlot"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledTimeSlotFree(@RequestParam("timeSlot") LocalDateTime timeSlot) {
        return new ResponseEntity<>(scheduledTrainingService.findByTimeSlotFree(timeSlot), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training with time slot between")
    @GetMapping(value = "/free", params = {"startDate", "endDate"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledBetweenFree(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return new ResponseEntity<>(scheduledTrainingService.findByTimeSlotBetweenFree(startDate, endDate), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name")
    @GetMapping(value = "/free", params = {"gymName"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameFree(@RequestParam("gymName") String gymName) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameFree(gymName), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by training name and training individual")
    @GetMapping(value = "/free", params = {"trainingName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledTrainingNameTrainingIndividualFree(@RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByTrainingNameAndTrainingIndividualFree(trainingName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and training name and training individual")
    @GetMapping(value = "/free", params = {"gymName", "trainingName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTrainingNameTrainingIndividualFree(@RequestParam("gymName") String gymName, @RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTrainingNameAndTrainingIndividualFree(gymName, trainingName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by training individual")
    @GetMapping(value = "/free", params = {"trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllSchedulledTrainingIndividualFree(@RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByTrainingIndividualFree(trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and training individual")
    @GetMapping(value = "/free", params = {"gymName", "trainingIndividual"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTrainingIndividualFree(@RequestParam("gymName") String gymName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTrainingIndividualFree(gymName, trainingIndividual), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and weekday")
    @GetMapping(value = "/free", params = {"gymName", "weekday"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameWeekdayFree(@RequestParam("gymName") String gymName, @RequestParam("weekday") String weekday) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndWeekdayFree(gymName, weekday), HttpStatus.OK);
    }

    @Operation(summary = "Get all scheduled training by gym name and time slot between")
    @GetMapping(value = "/free", params = {"gymName", "startDate", "endDate"})
    public ResponseEntity<List<ScheduledTrainingDto>> getAllScheduledGymNameTimeSlotBetweenFree(@RequestParam("gymName") String gymName, @RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return new ResponseEntity<>(scheduledTrainingService.findByGymNameAndTimeSlotBetweenFree(gymName, startDate, endDate), HttpStatus.OK);
    }

    @Operation(summary = "Create a scheduled training")
    @CheckSecurity(roles = {"ROLEMANAGER"})
    @PostMapping
    public ResponseEntity<ScheduledTrainingDto> createScheduled(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ScheduledTrainingCreateDto scheduledTrainingCreateDto) {
        return new ResponseEntity<>(scheduledTrainingService.create(scheduledTrainingCreateDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a scheduled training")
    @CheckSecurity(roles = {"ROLEMANAGER"})
    @PutMapping(params = {"id"})
    public ResponseEntity<ScheduledTrainingDto> updateScheduledById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id, @RequestBody @Valid ScheduledTrainingUpdateDto scheduledTrainingUpdateDto) {
        return new ResponseEntity<>(scheduledTrainingService.update(id, scheduledTrainingUpdateDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete a scheduled training")
    @CheckSecurity(roles = {"ROLEMANAGER"})
    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteScheduledById(@RequestHeader("Authorization") String authorization, @RequestParam("id") Long id) {
        scheduledTrainingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
