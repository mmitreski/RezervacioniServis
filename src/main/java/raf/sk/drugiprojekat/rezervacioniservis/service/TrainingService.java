package raf.sk.drugiprojekat.rezervacioniservis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.TrainingUpdateDto;

import java.util.List;
public interface TrainingService {
    Page<TrainingDto> findAll(Pageable pageable);
    TrainingDto findById(Long id);
    TrainingDto findByNameAndIndividual(String name, Boolean individual);
    TrainingDto add(TrainingCreateDto trainingCreateDto);
    TrainingDto update(Long id, TrainingUpdateDto trainingUpdateDto);
    void deleteById(Long id);
}
