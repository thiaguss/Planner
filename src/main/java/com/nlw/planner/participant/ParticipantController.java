package com.nlw.planner.participant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantRepository repository;

    public ParticipantController(ParticipantRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participants> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload){
        Optional<Participants> participants = repository.findById(id);

        if (participants.isPresent()){
            Participants rawparticipant = participants.get();
            rawparticipant.setIsConfirmed(true);
            rawparticipant.setName(payload.name());

            repository.save(rawparticipant);

            return ResponseEntity.ok(rawparticipant);
        }

        return ResponseEntity.notFound().build();

    }
}
