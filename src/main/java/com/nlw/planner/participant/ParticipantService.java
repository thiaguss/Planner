package com.nlw.planner.participant;

import com.nlw.planner.trip.Trips;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public void registerParticipantsToTrip(List<String> participantsToIvite, Trips trip){
        List<Participants> participants = participantsToIvite.stream().map(email -> new Participants(email, trip)).toList();

        repository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToTrip(String email, Trips trip){
        Participants newParticipant = new Participants();
        repository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public void triggerConfirmationEmailToParticipant(String email) {}
}
