package raf.sk.drugiprojekat.rezervacioniservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Gym;

import java.util.List;
import java.util.Optional;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {
    Optional<Gym> findGymById(Long id);
    Optional<Gym> findGymByName(String name);
    List<Gym> findAllGymsByManagerId(Long managerId);
}
