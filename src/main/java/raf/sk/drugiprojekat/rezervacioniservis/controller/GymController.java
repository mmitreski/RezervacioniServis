package raf.sk.drugiprojekat.rezervacioniservis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.GymService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/gym")
@AllArgsConstructor
public class GymController {
    private GymService gymService;
    @Operation(summary = "Get all gym")
    @Parameters({
            @Parameter(name = "page", description = "What page number you want", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "Number of items to return", in = ParameterIn.QUERY),
            @Parameter(name = "sort", in = ParameterIn.QUERY,
                    description = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<GymDto>> getAllGyms(Pageable pageable) {
        return new ResponseEntity<>(gymService.findAll(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get gym by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<GymDto> getGymById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(gymService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get gym by name")
    @GetMapping(params = {"name"})
    public ResponseEntity<GymDto> getGymByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(gymService.findByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Get gyms by managerId")
    @GetMapping(params = {"managerId"})
    public ResponseEntity<List<GymDto>> getGymsByManagerId(@RequestParam("managerId") Long managerId) {
        return new ResponseEntity<>(gymService.findByManagerId(managerId), HttpStatus.OK);
    }

    @Operation(summary = "Create a gym")
    @PostMapping
    @CheckSecurity(roles = {"ROLEADMIN"})
    public ResponseEntity<GymDto> createGym(@RequestHeader("Authorization") String authorization, @RequestBody @Valid GymCreateDto gymCreateDto) {
        return new ResponseEntity<>(gymService.add(gymCreateDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a gym")
    @PutMapping
    @CheckSecurity(roles = {"ROLEADMIN", "ROLEMANAGER"})
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization, @RequestParam("id") @Positive Long id, @RequestBody(required = false) GymUpdateDto gymUpdateDto) {
        return new ResponseEntity<>(gymService.update(id, gymUpdateDto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete a gym")
    @DeleteMapping
    @CheckSecurity(roles = {"ROLEADMIN"})
    public ResponseEntity<?> deleteGym(@RequestHeader("Authorization") String authorization,@RequestParam("id") @Positive Long id) {
        gymService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
