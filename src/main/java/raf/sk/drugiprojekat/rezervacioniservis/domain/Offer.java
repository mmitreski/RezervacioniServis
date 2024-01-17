package raf.sk.drugiprojekat.rezervacioniservis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Gym gym;
    @ManyToOne
    private Training training;
    private Long price;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ScheduledTraining> scheduledTraining;
}
