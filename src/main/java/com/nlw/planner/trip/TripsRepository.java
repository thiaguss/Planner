package com.nlw.planner.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripsRepository extends JpaRepository<Trips, UUID> {
}
