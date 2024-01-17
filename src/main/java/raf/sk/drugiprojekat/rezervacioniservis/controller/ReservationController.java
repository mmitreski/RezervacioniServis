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
import raf.sk.drugiprojekat.rezervacioniservis.service.ReservationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
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
    public ResponseEntity<Page<ReservationDto>> getAllReservations(Pageable pageable) {
        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get reservation by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<ReservationDto> getReservationId(@RequestParam("id") Long id) {
        return new ResponseEntity<>(reservationService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all reservations by scheduled training id")
    @GetMapping(params = {"scheduledTrainingId"})
    public ResponseEntity<List<ReservationDto>> getAllReservationsScheduledTrainingId(@RequestParam("scheduledTrainingId") Long scheduledTrainingId) {
        return new ResponseEntity<>(reservationService.findAllByScheduledTrainingId(scheduledTrainingId), HttpStatus.OK);
    }

    @Operation(summary = "Get all reservations by client id")
    @GetMapping(params = {"clientId"})
    public ResponseEntity<List<ReservationDto>> getAllReservationsClientId(@RequestParam("clientId") Long clientId) {
        return new ResponseEntity<>(reservationService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @Operation(summary = "Create a reservation")
    @PostMapping
    @CheckSecurity(roles = {"ROLECLIENT"})
    public ResponseEntity<ReservationDto> createOffer(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ReservationCreateDto reservationCreateDto) {
        ResponseEntity<ReservationDto> responseEntity = new ResponseEntity<>(reservationService.add(reservationCreateDto), HttpStatus.CREATED);
        return responseEntity;
    }

    @Operation(summary = "Delete a reservation")
    @DeleteMapping
    @CheckSecurity(roles = {"ROLECLIENT"})
    public ResponseEntity<?> deleteOffer(@RequestHeader("Authorization") String authorization,@RequestParam("id") @Positive Long id) {
        reservationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
