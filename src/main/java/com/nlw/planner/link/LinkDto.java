package com.nlw.planner.link;

import java.util.UUID;

public record LinkDto(
        UUID id,
        String title,
        String url
) {
}
