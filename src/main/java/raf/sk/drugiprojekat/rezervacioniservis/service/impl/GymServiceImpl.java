package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.GymMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.GymRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.GymService;

import java.util.List;

@Service
@AllArgsConstructor
public class GymServiceImpl implements GymService {

    private GymMapper gymMapper;
    private GymRepository gymRepository;

    @Override
    public Page<GymDto> findAll(Pageable pageable) {
        return gymRepository.findAll(pageable).map(gymMapper::gymToGymDto);
    }

    @Override
    public GymDto findById(Long id) {
        return gymRepository.findById(id).map(gymMapper::gymToGymDto).orElseThrow(() -> new NotFoundException(String
                    .format("GYM [id: %d] NOT FOUND.", id)));
    }

    @Override
    public GymDto findByName(String name) {
        return gymRepository.findGymByName(name).map(gymMapper::gymToGymDto).orElseThrow(() -> new NotFoundException(String
                .format("GYM [name: %s] NOT FOUND.", name)));
    }

    @Override
    public List<GymDto> findByManagerId(Long managerId) {
        return gymRepository.findAllGymsByManagerId(managerId).stream().map(gymMapper::gymToGymDto).toList();
    }

    @Override
    public GymDto add(GymCreateDto gymCreateDto) {
        return gymMapper.gymToGymDto(gymRepository.save(gymMapper.gymCreateDtoToGym(gymCreateDto)));
    }

    @Override
    public GymDto update(Long id, GymUpdateDto gymUpdateDto) {
        return gymMapper.gymToGymDto(gymRepository.save(gymMapper.gymUpdateDtoToGym(id, gymUpdateDto)));
    }

    @Override
    public void deleteById(Long id) {
        gymRepository.deleteById(id);
    }
}
