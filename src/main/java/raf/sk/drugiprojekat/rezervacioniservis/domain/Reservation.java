package raf.sk.drugiprojekat.rezervacioniservis.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ScheduledTraining scheduledTraining;
    private Long discountedPrice;
    private Long clientId;
}
