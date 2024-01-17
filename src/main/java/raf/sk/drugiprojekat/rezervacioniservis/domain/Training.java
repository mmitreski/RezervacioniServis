package raf.sk.drugiprojekat.rezervacioniservis.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean individual;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Offer> scheduledTrainingList;
}
