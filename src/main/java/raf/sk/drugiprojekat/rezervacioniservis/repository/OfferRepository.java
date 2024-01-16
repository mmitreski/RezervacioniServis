package raf.sk.drugiprojekat.rezervacioniservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Offer;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<Offer> findOfferById(Long id);
    Optional<Offer> findOfferByGym_idAndTraining_id(Long gym_id, Long training_id);
    List<Offer> findAllOffersByGym_id(Long gym_id);
    List<Offer> findAllOffersByGym_name(String gym_name);
    List<Offer> findAllOffersByTraining_id(Long training_id);
    List<Offer> findAllOffersByTraining_nameAndTraining_individual(String training_name, Boolean training_individual);
}
