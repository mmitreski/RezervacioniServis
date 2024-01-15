package raf.sk.drugiprojekat.rezervacioniservis.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class ScheduledTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Offer offer;
    private LocalDateTime timeSlot;
    private Long reservedSpots;
    @OneToMany
    private List<Reservation> reservations;
}
