package com.nlw.planner.participant;

import com.nlw.planner.trip.Trips;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trips trip;

    public Participants (String email, Trips trip){
        this.email =email;
        this.trip = trip;
    }
}
