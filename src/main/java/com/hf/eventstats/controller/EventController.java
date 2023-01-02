package com.hf.eventstats.controller;


import com.hf.eventstats.model.Event;
import com.hf.eventstats.service.EventService;
import com.hf.eventstats.service.PayloadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/event")
@RestController
public class EventController {
    private final EventService eventService;
    private final PayloadService payloadService;

    public EventController(EventService eventService, PayloadService payloadService) {
        this.eventService = eventService;
        this.payloadService = payloadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String addEvents(@RequestBody String payload) {
        List<Event> events = payloadService.processPayload(payload);
        eventService.addEvents(events);
        return events.toString();
    }
}