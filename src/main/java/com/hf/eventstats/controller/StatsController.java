package com.hf.eventstats.controller;

import com.hf.eventstats.model.Stats;
import com.hf.eventstats.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/stats")
@RestController
public class StatsController {
    private final EventService eventService;

    public StatsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getStats() {
        Stats stats = eventService.getStats();
        return stats.toString();
    }
}
