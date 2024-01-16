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
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity;
import raf.sk.drugiprojekat.rezervacioniservis.service.GymService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/gym")
@AllArgsConstructor
public class GymController {
    private GymService gymService;
    @ApiOperation(value = "Get all gym")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<GymDto>> getAllGyms(Pageable pageable) {
        return new ResponseEntity<>(gymService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<GymDto> getGymById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(gymService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gym by name")
    @GetMapping(params = {"name"})
    public ResponseEntity<GymDto> getGymByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(gymService.findByName(name), HttpStatus.OK);
    }

    @ApiOperation(value = "Get gyms by managerId")
    @GetMapping(params = {"managerId"})
    public ResponseEntity<List<GymDto>> getGymsByManagerId(@RequestParam("managerId") Long managerId) {
        return new ResponseEntity<>(gymService.findByManagerId(managerId), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a gym")
    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<GymDto> createGym(@RequestHeader("Authorization") String authorization, @RequestBody @Valid GymCreateDto gymCreateDto) {
        return new ResponseEntity<>(gymService.add(gymCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a gym")
    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<GymDto> updateGym(@RequestHeader("Authorization") String authorization, @RequestParam("id") @Positive Long id, @RequestBody(required = false) GymUpdateDto gymUpdateDto) {
        return new ResponseEntity<>(gymService.update(id, gymUpdateDto), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete a gym")
    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<?> deleteGym(@RequestHeader("Authorization") String authorization,@RequestParam("id") @Positive Long id) {
        gymService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
