package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Training;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;

@Component
public class TrainingMapper {
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
}
