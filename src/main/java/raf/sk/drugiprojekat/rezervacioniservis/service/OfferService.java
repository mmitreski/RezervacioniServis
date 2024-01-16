package raf.sk.drugiprojekat.rezervacioniservis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferUpdateDto;

import java.util.List;

public interface OfferService {
    Page<OfferDto> findAll(Pageable pageable);
    OfferDto findById(Long id);
    OfferDto findByGymIdTrainingId(Long gymId, Long trainingId);
    List<OfferDto> findAllOfferGymId(Long gymId);
    List<OfferDto> findAllOffersGymName(String gymName);
    List<OfferDto> findAllOffersTrainingId(Long trainingId);
    List<OfferDto> findAllOffersTrainingNameTrainingIndividual(String trainingName, Boolean trainingIndividual);
    OfferDto add(OfferCreateDto offerCreateDto);
    OfferDto update(Long id, OfferUpdateDto offerUpdateDto);
    void deleteById(Long id);

}
