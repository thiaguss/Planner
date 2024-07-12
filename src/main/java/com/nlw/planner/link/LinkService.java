package com.nlw.planner.link;

import com.nlw.planner.activity.ActivityDto;
import com.nlw.planner.trip.Trips;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {
        this.repository = repository;
    }


    public LinkResponse registerLink(LinkRequestPayload payload, Trips trips){
        Link link = new Link(payload.title(), payload.url(), trips);

        repository.save(link);

        return new LinkResponse(link.getId());
    }

    public List<LinkDto> getAllLinks(UUID id) {
        return repository.getLinksFromId(id).stream().map(links -> new LinkDto(
                links.getId(),
                links.getTitle(),
                links.getUrl()
        )).toList();
    }
}
