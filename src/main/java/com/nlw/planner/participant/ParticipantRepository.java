package com.nlw.planner.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participants, UUID> {
    List<Participants> findByTripId(UUID triId);
}
