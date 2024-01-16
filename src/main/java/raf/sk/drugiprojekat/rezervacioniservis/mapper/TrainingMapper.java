package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Training;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.TrainingRepository;

@Component
@AllArgsConstructor
public class TrainingMapper {
    private TrainingRepository trainingRepository;
    public TrainingDto trainingToTrainingDto(Training training) {
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setId(training.getId());
        trainingDto.setName(training.getName());
        trainingDto.setIndividual(training.getIndividual());
        return trainingDto;
    }

    public Training trainingCreateDtoToTraining(TrainingCreateDto trainingCreateDto) {
        Training training = new Training();
        training.setIndividual(trainingCreateDto.getIndividual());
        training.setName(trainingCreateDto.getName());
        return training;
    }

    public Training trainingUpdateDtoToTraining(Long id, TrainingUpdateDto trainingUpdateDto) {
        Training training = trainingRepository.findTrainingById(id).orElseThrow(() -> new NotFoundException(String
                .format("TRAINING [id: %d] NOT FOUND.", id)));
        if(trainingUpdateDto.getName() != null) {
            training.setName(trainingUpdateDto.getName());
        }
        if(trainingUpdateDto.getIndividual() != null) {
            training.setIndividual(trainingUpdateDto.getIndividual());
        }
        return training;
    }
}
