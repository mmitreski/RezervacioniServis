package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Offer;
import raf.sk.drugiprojekat.rezervacioniservis.domain.ScheduledTraining;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.OfferRepository;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ScheduledTrainingRepository;

@Component
@AllArgsConstructor
public class ScheduledTrainingMapper {
    private OfferMapper offerMapper;
    private OfferRepository offerRepository;
    private ScheduledTrainingRepository scheduledTrainingRepository;

    public ScheduledTrainingDto scheduledTrainingToScheduledTrainingDto(ScheduledTraining scheduledTraining) {
        ScheduledTrainingDto scheduledTrainingDto = new ScheduledTrainingDto();
        scheduledTrainingDto.setId(scheduledTraining.getId());
        scheduledTrainingDto.setOfferDto(offerMapper.offerToOfferDto(scheduledTraining.getOffer()));
        scheduledTrainingDto.setReservedSpots(scheduledTraining.getReservedSpots());
        scheduledTrainingDto.setTimeSlot(scheduledTraining.getTimeSlot());
        scheduledTrainingDto.setMax(scheduledTraining.getMax());
        return scheduledTrainingDto;
    }

    public ScheduledTraining scheduledTrainingCreateDtoToScheduledTraining(ScheduledTrainingCreateDto scheduledTrainingCreateDto) {
        ScheduledTraining scheduledTraining = new ScheduledTraining();
        scheduledTraining.setReservedSpots(0L);
        scheduledTraining.setMax(scheduledTrainingCreateDto.getMax());
        scheduledTraining.setTimeSlot(scheduledTrainingCreateDto.getTimeSlot());
        scheduledTraining.setOffer(offerRepository.findOfferById(scheduledTrainingCreateDto.getOfferId()).orElseThrow(() -> new NotFoundException(String
                .format("OFFER [id: %d] NOT FOUND.", scheduledTrainingCreateDto.getOfferId()))));
        return scheduledTraining;
    }

    public ScheduledTraining scheduledTrainingUpdateDtoToScheduledTraining(Long id, ScheduledTrainingUpdateDto scheduledTrainingUpdateDto) {
        ScheduledTraining scheduledTraining = scheduledTrainingRepository.findScheduledTrainingById(id).orElseThrow(() -> new NotFoundException(String
                .format("SCHEDULED TRAINING [id: %d] NOT FOUND.", id)));
        if(scheduledTrainingUpdateDto.getTimeSlot() != null) {
            scheduledTraining.setTimeSlot(scheduledTrainingUpdateDto.getTimeSlot());
        }
        if(scheduledTrainingUpdateDto.getMax() != null) {
            scheduledTraining.setMax(scheduledTrainingUpdateDto.getMax());
        }
        if(scheduledTrainingUpdateDto.getOfferId() != null) {
            Offer offer = offerRepository.findOfferById(scheduledTrainingUpdateDto.getOfferId()).orElseThrow(() -> new NotFoundException(String
                    .format("OFFER [id: %d] NOT FOUND.", scheduledTrainingUpdateDto.getOfferId())));
            scheduledTraining.setOffer(offer);
        }
        if(scheduledTrainingUpdateDto.getMax() != null) {
            scheduledTraining.setMax(scheduledTrainingUpdateDto.getMax());
        }
        return scheduledTraining;
    }
}
