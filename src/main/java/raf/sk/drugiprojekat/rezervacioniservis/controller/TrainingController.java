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
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.TrainingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/training")
@AllArgsConstructor
public class TrainingController {
    private TrainingService trainingService;

    @ApiOperation(value = "Get all trainings")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<TrainingDto>> getAllTrainings(Pageable pageable) {
        return new ResponseEntity<>(trainingService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get training by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<TrainingDto> getById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(trainingService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get training by name and individual")
    @GetMapping(params = {"name", "individual"})
    public ResponseEntity<TrainingDto> getByNameAndIndividual(@RequestParam("name") String name, @RequestParam("individual") Boolean individual) {
        return new ResponseEntity<>(trainingService.findByNameAndIndividual(name, individual), HttpStatus.OK);
    }

    @ApiOperation(value = "Create training")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestHeader("authorization") String authorization, @RequestBody @Valid TrainingCreateDto trainingCreateDto) {
        return new ResponseEntity<>(trainingService.add(trainingCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update training")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestHeader("authorization") String authorization,@RequestParam("id") Long id, @RequestBody(required = false) @Valid TrainingUpdateDto trainingUpdateDto) {
        return new ResponseEntity<>(trainingService.update(id, trainingUpdateDto), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete training")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<?> deleteTraining(@RequestHeader("authorization") String authorization, @RequestParam("id") Long id) {
        trainingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
