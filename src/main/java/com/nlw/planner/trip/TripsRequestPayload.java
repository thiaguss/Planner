package com.nlw.planner.trip;

import java.util.List;

public record TripsRequestPayload(
        String destination,
        String starts_at,
        String ends_at,
        List<String> emails_to_invite,
        String owner_email,
        String owner_name
) {
}
