package raf.sk.drugiprojekat.rezervacioniservis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long trainerNumber;
    private Long managerId;
    private Long trainingDiscountNumber;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Offer> scheduledTrainingList;
}
