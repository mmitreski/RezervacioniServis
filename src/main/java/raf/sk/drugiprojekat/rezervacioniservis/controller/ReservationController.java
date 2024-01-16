package raf.sk.drugiprojekat.rezervacioniservis.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.drugiprojekat.rezervacioniservis.dto.*;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.ReservationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;
    @ApiOperation(value = "Get all reservations")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<ReservationDto>> getAllReservations(Pageable pageable) {
        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get reservation by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<ReservationDto> getReservationId(@RequestParam("id") Long id) {
        return new ResponseEntity<>(reservationService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all reservations by scheduled training id")
    @GetMapping(params = {"scheduledTrainingId"})
    public ResponseEntity<List<ReservationDto>> getAllReservationsScheduledTrainingId(@RequestParam("scheduledTrainingId") Long scheduledTrainingId) {
        return new ResponseEntity<>(reservationService.findAllByScheduledTrainingId(scheduledTrainingId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all reservations by client id")
    @GetMapping(params = {"clientId"})
    public ResponseEntity<List<ReservationDto>> getAllReservationsClientId(@RequestParam("clientId") Long clientId) {
        return new ResponseEntity<>(reservationService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a reservation")
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> createOffer(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ReservationCreateDto reservationCreateDto) {
        return new ResponseEntity<>(reservationService.add(reservationCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a reservation")
    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<?> deleteOffer(@RequestHeader("Authorization") String authorization,@RequestParam("id") @Positive Long id) {
        reservationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
