package com.nlw.planner.activity;

import com.nlw.planner.trip.Trips;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }


    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trips trips){
        Activity activity = new Activity(payload.title(), payload.occurs_at(), trips);

        repository.save(activity);

        return new ActivityResponse(activity.getId());
    }

    public List<ActivityDto> getActivitiesFromId(UUID id) {
        return repository.findByTripId(id).stream().map(activities-> new ActivityDto(
                activities.getId(),
                activities.getTitle(),
                activities.getOccursAt()
        )).toList();
    }
}
