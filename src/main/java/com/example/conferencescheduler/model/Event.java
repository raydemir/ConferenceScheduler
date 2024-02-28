package com.example.conferencescheduler.model;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class Event implements Comparable<Event> {
    private String topic;
    private int duration;
    private LocalTime startTime;

    public Event(String topic, int duration) {
        this.topic = topic;
        this.duration = duration;
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.duration, other.duration);
    }

    public LocalTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    @Override
    public String toString() {
        StringBuilder scheduleStringBuilder = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConstants.TIME_PATTERN);
        String startTimeString = startTime.format(formatter);
        scheduleStringBuilder.append(startTimeString);

        scheduleStringBuilder.append(StringConstants.SPACE);
        scheduleStringBuilder.append(topic);

        if (!topic.equals(StringConstants.LUNCH) && !topic.equals(StringConstants.NETWORKING_EVENT)) {
            scheduleStringBuilder.append(StringConstants.SPACE);
            scheduleStringBuilder.append(duration == 5 ? StringConstants.LIGHTNING : duration + StringConstants.MINUTE);
        } else {
            scheduleStringBuilder.append(" 60min");
        }

        return scheduleStringBuilder.toString();
    }
}
