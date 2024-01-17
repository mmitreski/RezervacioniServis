package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.OfferMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.OfferRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.OfferService;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private OfferMapper offerMapper;

    @Override
    public Page<OfferDto> findAll(Pageable pageable) {
        return offerRepository.findAll(pageable).map(offerMapper::offerToOfferDto);
    }

    @Override
    public OfferDto findById(Long id) {
        return offerRepository.findById(id).map(offerMapper::offerToOfferDto).orElseThrow(() -> new NotFoundException(String.format("OFFER [id: %d] NOT FOUND.", id)));
    }

    @Override
    public OfferDto findByGymIdTrainingId(Long gymId, Long trainingId) {
        return offerRepository.findOfferByGym_idAndTraining_id(gymId, trainingId).map(offerMapper::offerToOfferDto).orElseThrow(() -> new NotFoundException(String.format("OFFER [gymId: %d, trainingId: %d] NOT FOUND.", gymId, trainingId)));
    }

    @Override
    public List<OfferDto> findAllOfferGymId(Long gymId) {
        return offerRepository.findAllOffersByGym_id(gymId).stream().map(offerMapper::offerToOfferDto).toList();
    }

    @Override
    public List<OfferDto> findAllOffersGymName(String gymName) {
        return offerRepository.findAllOffersByGym_name(gymName).stream().map(offerMapper::offerToOfferDto).toList();
    }

    @Override
    public List<OfferDto> findAllOffersTrainingId(Long trainingId) {
        return offerRepository.findAllOffersByTraining_id(trainingId).stream().map(offerMapper::offerToOfferDto).toList();
    }

    @Override
    public List<OfferDto> findAllOffersTrainingNameTrainingIndividual(String trainingName, Boolean trainingIndividual) {
        return offerRepository.findAllOffersByTraining_nameAndTraining_individual(trainingName, trainingIndividual).stream().map(offerMapper::offerToOfferDto).toList();
    }

    @Override
    public OfferDto add(OfferCreateDto offerCreateDto) {
        return offerMapper.offerToOfferDto(offerRepository.save(offerMapper.offerCreateDtoToOffer(offerCreateDto)));
    }

    @Override
    public OfferDto update(Long id, OfferUpdateDto offerUpdateDto) {
        return offerMapper.offerToOfferDto(offerRepository.save(offerMapper.offerUpdateDtoToOffer(id, offerUpdateDto)));
    }

    @Override
    public void deleteById(Long id) {
        offerRepository.deleteById(id);
    }
}
