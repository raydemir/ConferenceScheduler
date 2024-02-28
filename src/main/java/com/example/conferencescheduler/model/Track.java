package com.example.conferencescheduler.model;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Track {
    public static final int MORNING_SESSION_DURATION = 180;
    public static final int AFTERNOON_SESSION_DURATION = 240;
    private List<Event> morningSession = new ArrayList<>();
    private List<Event> afternoonSession = new ArrayList<>();

    public boolean addEvent(Event event) {
        if (getFreeTimeInAfternoonSession() >= event.getDuration()) {
            if (afternoonSession.isEmpty()) {
                event.setStartTime(LocalTime.of(13, 0)); // Start afternoon session at 1:00 PM
            } else {
                Event lastEvent = afternoonSession.get(afternoonSession.size() - 1);
                event.setStartTime(lastEvent.getEndTime());
            }
            afternoonSession.add(event);
            return true;
        } else if (getFreeTimeInMorningSession() >= event.getDuration()) {
            if (morningSession.isEmpty()) {
                event.setStartTime(LocalTime.of(9, 0)); // Start morning session at 9:00 AM
            } else {
                Event lastEvent = morningSession.get(morningSession.size() - 1);
                event.setStartTime(lastEvent.getEndTime());
            }
            morningSession.add(event);
            return true;
        } else {
            return false;
        }
    }

    private int getScheduledTime(List<Event> events) {
        int scheduledTime = 0;
        for (Event event : events) {
            scheduledTime += event.getDuration();
        }
        return scheduledTime;
    }

    private int getFreeTimeInAfternoonSession() {
        return AFTERNOON_SESSION_DURATION - getScheduledTime(afternoonSession);
    }

    private int getFreeTimeInMorningSession() {
        return MORNING_SESSION_DURATION - getScheduledTime(morningSession);
    }

    public List<String> getSchedule() {
        List<String> schedule = new ArrayList<>();
        for (Event event : morningSession) {
            schedule.add(event.toString());
        }
        schedule.add(StringConstants.LUNCH + " 60min");
        for (Event event : afternoonSession) {
            schedule.add(event.toString());
        }
        // Add networking event only if there's time available
        if (!afternoonSession.isEmpty() && afternoonSession.get(afternoonSession.size() - 1).getEndTime().isBefore(LocalTime.of(16, 0))) {
            schedule.add(StringConstants.NETWORKING_EVENT + " 60min");
        }
        return schedule;
    }

    public void addNetworkingEvent() {
        Event networkingEvent = new Event(StringConstants.NETWORKING_EVENT, 60);
        if (!afternoonSession.isEmpty()) {
            Event lastEvent = afternoonSession.get(afternoonSession.size() - 1);
            LocalTime startTime = lastEvent.getEndTime().isBefore(LocalTime.of(16, 0)) ?
                    LocalTime.of(16, 0) : lastEvent.getEndTime();
            networkingEvent.setStartTime(startTime);
            afternoonSession.add(networkingEvent);
        }
    }
}
