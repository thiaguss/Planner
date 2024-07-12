package com.nlw.planner.trip;


import com.nlw.planner.activity.ActivityDto;
import com.nlw.planner.activity.ActivityRequestPayload;
import com.nlw.planner.activity.ActivityResponse;
import com.nlw.planner.activity.ActivityService;
import com.nlw.planner.link.LinkDto;
import com.nlw.planner.link.LinkRequestPayload;
import com.nlw.planner.link.LinkResponse;
import com.nlw.planner.link.LinkService;
import com.nlw.planner.participant.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripsController {

    private final ParticipantService participantService;
    private final TripsRepository repository;
    private final ActivityService activityService;
    private final LinkService linkService;

    public TripsController(ParticipantService participantService, TripsRepository repository, ActivityService activityService, LinkService linkService) {
        this.participantService = participantService;
        this.repository = repository;
        this.activityService = activityService;
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripsRequestPayload payload){
        Trips newTrip = new Trips(payload);

        repository.save(newTrip);
        participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trips> getTripDetails(@PathVariable UUID id){
        Optional<Trips> trips = repository.findById(id);

        return trips.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trips> updateTrip(@PathVariable UUID id, @RequestBody TripsRequestPayload payload){
        Optional<Trips> trip = repository.findById(id);

        if (trip.isPresent()){
            Trips newTrip = trip.get();
            newTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setDestination(payload.destination());

            repository.save(newTrip);

            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trips> confirmTrip(@PathVariable UUID id){
        Optional<Trips> trip = repository.findById(id);

        if (trip.isPresent()){
            Trips newTrip = trip.get();
            newTrip.setIsConfirmed(true);

            repository.save(newTrip);
            participantService.triggerConfirmationEmailToParticipants(id);
            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipants(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload){
        Optional<Trips> trip = repository.findById(id);

        if (trip.isPresent()){
            Trips newTrip = trip.get();

            ParticipantCreateResponse participantResponse = participantService.registerParticipantToTrip(payload.email(), newTrip);

            if (newTrip.getIsConfirmed()) participantService.triggerConfirmationEmailToParticipant(payload.email());

            return ResponseEntity.ok(participantResponse );
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDto>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantDto> participantsList = participantService.getAllParticipantsFromTrip(id);

        return ResponseEntity.ok(participantsList);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivities(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload){
        Optional<Trips> trip = repository.findById(id);

        if (trip.isPresent()){
            Trips newTrip = trip.get();

            ActivityResponse response = activityService.registerActivity(payload, newTrip);


            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDto>> getAllActivities(@PathVariable UUID id){
        List<ActivityDto> activityDtos = activityService.getActivitiesFromId(id);

        return ResponseEntity.ok(activityDtos);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload){
        Optional<Trips> trip = repository.findById(id);

        if (trip.isPresent()){
            Trips newTrip = trip.get();

            LinkResponse response = linkService.registerLink(payload, newTrip);


            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDto>> getAlllinks(@PathVariable UUID id){
        List<LinkDto> linkDtos = linkService.getAllLinks(id);

        return ResponseEntity.ok(linkDtos);
    }
}
