package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.ScheduledTraining;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.OfferRepository;

@Component
@AllArgsConstructor
public class ScheduledTrainingMapper {
    private OfferMapper offerMapper;
    private OfferRepository offerRepository;

    public ScheduledTrainingDto scheduledTrainingToScheduledTrainingDto(ScheduledTraining scheduledTraining) {
        ScheduledTrainingDto scheduledTrainingDto = new ScheduledTrainingDto();
        scheduledTrainingDto.setId(scheduledTraining.getId());
        scheduledTrainingDto.setOfferDto(offerMapper.offerToOfferDto(scheduledTraining.getOffer()));
        scheduledTrainingDto.setReservedSpots(scheduledTraining.getReservedSpots());
        scheduledTrainingDto.setTimeSlot(scheduledTraining.getTimeSlot());
        return scheduledTrainingDto;
    }

    public ScheduledTraining scheduledTrainingCreateDtoToScheduledTraining(ScheduledTrainingCreateDto scheduledTrainingCreateDto) {
        ScheduledTraining scheduledTraining = new ScheduledTraining();
        scheduledTraining.setReservedSpots(0L);
        scheduledTraining.setTimeSlot(scheduledTrainingCreateDto.getTimeSlot());
        scheduledTraining.setOffer(offerRepository.findOfferById(scheduledTrainingCreateDto.getOfferId()).orElseThrow(() -> new NotFoundException(String
                .format("OFFER [id: %d] NOT FOUND.", scheduledTrainingCreateDto.getOfferId()))));
        return scheduledTraining;
    }
}
