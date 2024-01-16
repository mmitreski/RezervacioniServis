package raf.sk.drugiprojekat.rezervacioniservis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymUpdateDto;

import java.util.List;

public interface GymService {
    Page<GymDto> findAll(Pageable pageable);
    GymDto findById(Long id);
    GymDto findByName(String name);
    List<GymDto> findByManagerId(Long managerId);
    GymDto add(GymCreateDto gymCreateDto);
    GymDto update(Long id, GymUpdateDto gymUpdateDto);
    void deleteById(Long id);
}
