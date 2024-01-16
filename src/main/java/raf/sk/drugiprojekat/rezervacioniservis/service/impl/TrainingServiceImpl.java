package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.TrainingMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.TrainingRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.TrainingService;

@Service
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;
    private TrainingMapper trainingMapper;

    @Override
    public Page<TrainingDto> findAll(Pageable pageable) {
        return trainingRepository.findAll(pageable).map(trainingMapper::trainingToTrainingDto);
    }

    @Override
    public TrainingDto findById(Long id) {
        return trainingRepository.findById(id).map(trainingMapper::trainingToTrainingDto).orElseThrow(() -> new NotFoundException(String.format("TRAINING [id: %d] NOT FOUND.", id)));
    }

    @Override
    public TrainingDto findByNameAndIndividual(String name, Boolean individual) {
        return trainingRepository.findTrainingByNameAndIndividual(name, individual).map(trainingMapper::trainingToTrainingDto).orElseThrow(() -> new NotFoundException(String.format("TRAINING [name: %s, individual: %b] NOT FOUND.", name, individual)));
    }

    @Override
    public TrainingDto add(TrainingCreateDto trainingCreateDto) {
        return trainingMapper.trainingToTrainingDto(trainingRepository.save(trainingMapper.trainingCreateDtoToTraining(trainingCreateDto)));
    }

    @Override
    public TrainingDto update(Long id, TrainingUpdateDto trainingUpdateDto) {
        return trainingMapper.trainingToTrainingDto(trainingRepository.save(trainingMapper.trainingUpdateDtoToTraining(id, trainingUpdateDto)));
    }

    @Override
    public void deleteById(Long id) {
        trainingRepository.deleteById(id);
    }
}
