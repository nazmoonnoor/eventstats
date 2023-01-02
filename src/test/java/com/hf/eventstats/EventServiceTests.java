package com.hf.eventstats;

import com.hf.eventstats.model.Event;
import com.hf.eventstats.model.Stats;
import com.hf.eventstats.service.EventService;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTests {

    Timestamp now = new Timestamp(1607341341814l); // 2020-12-07 12:42:21.814

    Timestamp ts1 = new Timestamp(1607341339814l); // 2020-12-07 12:42:19.814
    Timestamp ts2 = new Timestamp(1607341331814l); // 2020-12-07 12:42:11.814
    Timestamp ts3 = new Timestamp(1607341271814l); // 2020-12-07 12:41:11.814
    Timestamp ts4 = new Timestamp(1607341338814l); // 2020-12-07 12:42:18.814

    Timestamp later = new Timestamp(1607353200000l); // 2020-12-07 16:00:00
    Timestamp ts5 = new Timestamp(1607353190000l); // 2020-12-07 15:59:50
    Timestamp ts6 = new Timestamp(1607353180000l); // 2020-12-07 15:59:40

    Event event1 = new Event(ts1, 0.0473002568, 1785397644); // diff in seconds with now : 2
    Event event2 = new Event(ts2, 0.0899538547, 1852154378); // diff in seconds with now : 10
    Event event3 = new Event(ts3, 0.0586780608, 111212767); // diff in seconds with now : 70
    Event event4 = new Event(ts4, 0.0302456915, 1760856792); // diff in seconds with now : 3

    Event event5 = new Event(ts5, 0.0302458888, 1760858888); // diff in seconds with later : 10
    Event event6 = new Event(ts6, 0.0302459999, 1760859999); // diff in seconds with later : 20

    List<Event> eventsAllInRange = Arrays.asList(event1, event2, event4);
    List<Event> eventsAllInRangeExceptOne = Arrays.asList(event1, event2, event3);
    List<Event> eventsAllInRangeFromLater = Arrays.asList(event5, event6);

    Timestamp notSoLate = new Timestamp(1607341395814l); // 2020-12-07 12:43:15 // from 12:43:15 to 12:42:15

    Timestamp ts7 = new Timestamp(1607341339814l); // 2020-12-07 12:42:19.814
    Timestamp ts8 = new Timestamp(1607341390814l); // 2020-12-07 12:43:10

    Event event7 = new Event(ts7, 0.0302457777, 1760857777); // diff in seconds with notSoLate : 54
    Event event8 = new Event(ts8, 0.0302456666, 1760856666); // diff in seconds with notSoLate : 5

    List<Event> eventsOverlapping = Arrays.asList(event7, event8);

    @Test
    void singleUpdateAllInRange() {
        EventService eventService = new EventService();
        eventService.updateData(eventsAllInRange, now);

        assertEquals(600000, eventService.getCountX().length);
        assertEquals(3, Arrays.stream(eventService.getCountX()).sum());

        assertEquals(600000, eventService.getCountY().length);
        assertEquals(3, Arrays.stream(eventService.getCountY()).sum());

        double expectedSumX = event1.getX() + event2.getX() + event4.getX();
        assertEquals(expectedSumX, Arrays.stream(eventService.getSumX()).sum());

        long expectedSumY = event1.getY() + event2.getY() + event4.getY();
        assertEquals(expectedSumY, Arrays.stream(eventService.getSumY()).sum());

        assertEquals(0.0473002568, eventService.getSumX()[2]);
        assertEquals(0.0899538547, eventService.getSumX()[10]);
        assertEquals(0.0302456915, eventService.getSumX()[3]);

        assertEquals(1, eventService.getCountX()[2]);
        assertEquals(1, eventService.getCountX()[10]);
        assertEquals(1, eventService.getCountX()[3]);

        assertEquals(1785397644, eventService.getSumY()[2]);
        assertEquals(1852154378, eventService.getSumY()[10]);
        assertEquals(1760856792, eventService.getSumY()[3]);

        assertEquals(1, eventService.getCountY()[2]);
        assertEquals(1, eventService.getCountY()[10]);
        assertEquals(1, eventService.getCountY()[3]);
    }

    @Test
    void singleUpdateAllInRangeExceptOne(){
        EventService eventService = new EventService();
        eventService.updateData(eventsAllInRangeExceptOne, now);

        assertEquals(3, Arrays.stream(eventService.getCountX()).sum());
        assertEquals(3, Arrays.stream(eventService.getCountY()).sum());

        assertEquals(0.0473002568, eventService.getSumX()[2]);
        assertEquals(0.0899538547, eventService.getSumX()[10]);

        assertEquals(1, eventService.getCountX()[2]);
        assertEquals(1, eventService.getCountX()[10]);

        assertEquals(1785397644, eventService.getSumY()[2]);
        assertEquals(1852154378, eventService.getSumY()[10]);

        assertEquals(1, eventService.getCountY()[2]);
        assertEquals(1, eventService.getCountY()[10]);
    }

    @Test
    void returnStats(){
        EventService eventService = new EventService();
        eventService.updateData(eventsAllInRange, now);
        Stats stats = eventService.getStats();
        assertEquals(3, stats.getTotal());
        double expectedSumX = event1.getX() + event2.getX() + event4.getX();
        long expectedSumY = event1.getY() + event2.getY() + event4.getY();
        assertEquals(expectedSumX, stats.getSumX());
        assertEquals(expectedSumY, stats.getSumY());

        double expectedAvgX = (event1.getX() + event2.getX() + event4.getX())/(double)Arrays.stream(eventService.getCountX()).sum();
        assertEquals(expectedAvgX, stats.getAvgX());

        double expectedAvgY = (event1.getY() + event2.getY() + event4.getY())/(double)Arrays.stream(eventService.getCountY()).sum();
        assertEquals(expectedAvgY, stats.getAvgY());

    }
}