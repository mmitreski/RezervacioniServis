package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Gym;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Offer;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Training;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.OfferUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.GymRepository;
import raf.sk.drugiprojekat.rezervacioniservis.repository.OfferRepository;
import raf.sk.drugiprojekat.rezervacioniservis.repository.TrainingRepository;

@Component
@AllArgsConstructor
public class OfferMapper {
    private GymMapper gymMapper;
    private TrainingMapper trainingMapper;
    private GymRepository gymRepository;
    private TrainingRepository trainingRepository;
    private OfferRepository offerRepository;

    public OfferDto offerToOfferDto(Offer offer) {
       OfferDto offerDto = new OfferDto();
       offerDto.setGymDto(gymMapper.gymToGymDto(offer.getGym()));
       offerDto.setTrainingDto(trainingMapper.trainingToTrainingDto(offer.getTraining()));
       offerDto.setId(offer.getId());
       offerDto.setPrice(offer.getPrice());
       return offerDto;
    }

    public Offer offerCreateDtoToOffer(OfferCreateDto offerCreateDto) {
        Offer offer = new Offer();
        offer.setPrice(offerCreateDto.getPrice());
        offer.setGym(gymRepository.findGymById(offerCreateDto.getGymId()).orElseThrow(() -> new NotFoundException(String
                .format("GYM [id: %d] NOT FOUND.", offerCreateDto.getGymId()))));
        offer.setTraining(trainingRepository.findTrainingById(offerCreateDto.getTrainingId()).orElseThrow(() -> new NotFoundException(String
                .format("TRAINING [id: %d] NOT FOUND.", offerCreateDto.getTrainingId()))));
        return offer;
    }

    public Offer offerUpdateDtoToOffer(Long id, OfferUpdateDto offerUpdateDto) {
        Offer offer = offerRepository.findOfferById(id).orElseThrow(() -> new NotFoundException(String.format("OFFER [id: %d] NOT FOUND.", id)));
        if(offerUpdateDto.getPrice() != null) {
            offer.setPrice(offerUpdateDto.getPrice());
        }
        if(offerUpdateDto.getGymId() != null) {
            Gym gym = gymRepository.findGymById(offerUpdateDto.getGymId()).orElseThrow(() -> new NotFoundException(String.format("GYM [id: %d] NOT FOUND.", offerUpdateDto.getGymId())));
            offer.setGym(gym);
        }
        if(offerUpdateDto.getTrainingId() != null) {
            Training training = trainingRepository.findTrainingById(offerUpdateDto.getGymId()).orElseThrow(() -> new NotFoundException(String.format("TRAINING [id: %d] NOT FOUND.", offerUpdateDto.getTrainingId())));
            offer.setTraining(training);
        }
        return offer;
    }
}
