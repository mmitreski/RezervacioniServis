package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Gym;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymUpdateDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.GymRepository;

@Component
@AllArgsConstructor
public class GymMapper {
    private GymRepository gymRepository;
    public GymDto gymToGymDto(Gym gym) {
        GymDto gymDto = new GymDto();
        gymDto.setDescription(gym.getDescription());
        gymDto.setId(gym.getId());
        gymDto.setManagerId(gym.getManagerId());
        gymDto.setTrainerNumber(gym.getTrainerNumber());
        gymDto.setName(gym.getName());
        return gymDto;
    }

    public Gym gymCreateDtoToGym(GymCreateDto gymCreateDto) {
        Gym gym = new Gym();
        gym.setManagerId(gymCreateDto.getManagerId());
        gym.setDescription(gymCreateDto.getDescription());
        gym.setName(gymCreateDto.getName());
        gym.setTrainerNumber(gymCreateDto.getTrainerNumber());
        return gym;
    }

    public Gym gymUpdateDtoToGym(Long id, GymUpdateDto gymUpdateDto) {
        Gym gym = gymRepository.findGymById(id).orElseThrow(() -> new NotFoundException(String
                .format("GYM [id: %d] NOT FOUND.", id)));
        if(gymUpdateDto.getDescription() != null) {
            gym.setDescription(gymUpdateDto.getDescription());
        }
        if(gymUpdateDto.getName() != null) {
            gym.setName(gym.getName());
        }
        if(gymUpdateDto.getManagerId() != null) {
            gym.setManagerId(gymUpdateDto.getManagerId());
        }
        if(gymUpdateDto.getTrainerNumber() != null) {
            gym.setTrainerNumber(gymUpdateDto.getTrainerNumber());
        }
        return gym;
    }
}
