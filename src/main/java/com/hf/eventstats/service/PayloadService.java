package com.hf.eventstats.service;

import com.hf.eventstats.model.Event;
import com.hf.eventstats.utils.PayloadUtility;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayloadService {

    public List<Event> processPayload(String payload) {
        List<Event> events = new ArrayList<>();
        String[] lines = payload.split("\n");
        for (String line : lines) {
            String[] data = line.split(",");
            String x = data[1];
            String y = data[2];
            try {
                Timestamp ts = PayloadUtility.createTimestampFromString(data[0]);
                if (PayloadUtility.isValid(x, y, ts)) {
                    Event event = new Event(ts, Double.parseDouble(x), Integer.parseInt(y));
                    events.add(event);
                }
            } catch (Exception e) {
                System.out.println(e.getClass());
            }
        }

        return events;
    }
}
