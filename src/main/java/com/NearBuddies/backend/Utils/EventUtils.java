package com.NearBuddies.backend.Utils;

import com.NearBuddies.backend.event.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class EventUtils {
    public static Event getEventFromString(String eventString) {
        Event event = new Event();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            event = objectMapper.readValue(eventString, Event.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }
        return event;
    }
}
