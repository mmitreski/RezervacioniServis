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
import raf.sk.drugiprojekat.rezervacioniservis.service.OfferService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/offer")
@AllArgsConstructor
public class OfferController {
    private OfferService offerService;
    @ApiOperation(value = "Get all offers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping("/all")
    public ResponseEntity<Page<OfferDto>> getAllOffers(Pageable pageable) {
        return new ResponseEntity<>(offerService.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Get offer by id")
    @GetMapping(params = {"id"})
    public ResponseEntity<OfferDto> getOfferById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(offerService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get offer by gym id and training id")
    @GetMapping(params = {"gymId", "trainingId"})
            public ResponseEntity<OfferDto> getOfferByGymIdTrainingId(@RequestParam("gymId") Long gymId, @RequestParam("trainingId") Long trainingId) {
        return new ResponseEntity<>(offerService.findByGymIdTrainingId(gymId,trainingId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all offers by gym id")
    @GetMapping(params = {"gymId"})
            public ResponseEntity<List<OfferDto>> getOffersByGymId(@RequestParam("gymId") Long gymId) {
        return new ResponseEntity<>(offerService.findAllOfferGymId(gymId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all offers by gym name")
    @GetMapping(params = {"gymName"})
    public ResponseEntity<List<OfferDto>> getOffersByGymName(@RequestParam("gymName") String gymName) {
        return new ResponseEntity<>(offerService.findAllOffersGymName(gymName), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all offers by training id")
    @GetMapping(params = {"trainingId"})
            public ResponseEntity<List<OfferDto>> getOffersByTrainingId(@RequestParam("trainingId") Long trainingId) {
        return new ResponseEntity<>(offerService.findAllOffersTrainingId(trainingId), HttpStatus.OK
        );
    }

    @ApiOperation(value = "Get all offers by training name and training individual")
    @GetMapping(params = {"trainingName", "trainingIndividual"})
            public ResponseEntity<List<OfferDto>> getOffersByTrainingNameTrainingIndividual(@RequestParam("trainingName") String trainingName, @RequestParam("trainingIndividual") Boolean trainingIndividual) {
        return new ResponseEntity<>(offerService.findAllOffersTrainingNameTrainingIndividual(trainingName, trainingIndividual), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a offer")
    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferCreateDto offerCreateDto) {
        return new ResponseEntity<>(offerService.add(offerCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a offer")
    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<OfferDto> updateOffer(@RequestHeader("Authorization") String authorization, @RequestParam("id") @Positive Long id, @RequestBody(required = false) OfferUpdateDto offerUpdateDto) {
        return new ResponseEntity<>(offerService.update(id, offerUpdateDto), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete a offer")
    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<?> deleteOffer(@RequestHeader("Authorization") String authorization,@RequestParam("id") @Positive Long id) {
        offerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
