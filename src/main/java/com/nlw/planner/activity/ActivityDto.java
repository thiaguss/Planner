package com.nlw.planner.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDto(
        UUID id,
        String title,
        LocalDateTime occurs_at
) {
}
