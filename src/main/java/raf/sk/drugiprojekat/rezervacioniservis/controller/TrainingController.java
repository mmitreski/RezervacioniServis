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
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.TrainingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/training")
@AllArgsConstructor
public class TrainingController {
    private TrainingService trainingService;

    @Operation(summary = "Get all gym")
    @Parameters({
            @Parameter(name = "page", description = "What page number you want", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "Number of items to return", in = ParameterIn.QUERY),
            @Parameter(name = "sort", in = ParameterIn.QUERY,
                    description = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<TrainingDto>> getAllTrainings(Pageable pageable) {
        return new ResponseEntity<>(trainingService.findAll(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get training by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<TrainingDto> getById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(trainingService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get training by name and individual")
    @GetMapping(params = {"name", "individual"})
    public ResponseEntity<TrainingDto> getByNameAndIndividual(@RequestParam("name") String name, @RequestParam("individual") Boolean individual) {
        return new ResponseEntity<>(trainingService.findByNameAndIndividual(name, individual), HttpStatus.OK);
    }

    @Operation(summary = "Create training")
    @CheckSecurity(roles = {"ROLEADMIN"})
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestHeader("authorization") String authorization, @RequestBody @Valid TrainingCreateDto trainingCreateDto) {
        return new ResponseEntity<>(trainingService.add(trainingCreateDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update training")
    @CheckSecurity(roles = {"ROLEADMIN"})
    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestHeader("authorization") String authorization,@RequestParam("id") Long id, @RequestBody(required = false) @Valid TrainingUpdateDto trainingUpdateDto) {
        return new ResponseEntity<>(trainingService.update(id, trainingUpdateDto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete training")
    @CheckSecurity(roles = {"ROLEADMIN"})
    @DeleteMapping
    public ResponseEntity<?> deleteTraining(@RequestHeader("authorization") String authorization, @RequestParam("id") Long id) {
        trainingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
