package com.nlw.planner.trip;


import com.nlw.planner.participant.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripsController {

    private final ParticipantService participantService;
    private final TripsRepository repository;

    public TripsController(ParticipantService service, TripsRepository repository) {
        this.participantService = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripsRequestPayload payload){
        Trips newTrip = new Trips(payload);

        repository.save(newTrip);
        participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip.getId());

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trips> getTripDetails(@PathVariable UUID id){
        Optional<Trips> trips = repository.findById(id);

        return trips.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
