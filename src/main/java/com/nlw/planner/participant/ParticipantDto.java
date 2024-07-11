package com.nlw.planner.participant;

import java.util.UUID;

public record ParticipantDto(
        UUID id,
        String nome,
        String email,
        Boolean isConfirmed
) {
}
