package com.nlw.planner.participant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    public void registerParticipantsToTrip(List<String> participantsToIvite, UUID tripId){}

    public void triggerConfirmationEmailToParticipants(UUID tripId){}
}
