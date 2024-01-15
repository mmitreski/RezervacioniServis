package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Gym;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.GymDto;

@Component
public class GymMapper {
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
}
