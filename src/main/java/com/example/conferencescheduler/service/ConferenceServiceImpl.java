package com.example.conferencescheduler.service;

import com.example.conferencescheduler.model.Event;
import com.example.conferencescheduler.model.StringConstants;
import com.example.conferencescheduler.model.Track;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final List<Event> eventList = new ArrayList<>();
    private final List<Track> trackList = new ArrayList<>();

    @Override
    public List<String> schedulePresentations(List<String> presentationRequests) {
        eventList.clear(); // Clear old event data
        trackList.clear(); // Clear old track data
        parseInput(presentationRequests);
        scheduleTasks();
        return printTracks();
    }

    private void parseInput(List<String> presentationRequests) {
        for (String request : presentationRequests) {
            String title;
            int durationMinutes;

            int lastSpaceIndex = request.lastIndexOf(' ');
            if (lastSpaceIndex != -1) {
                title = request.substring(0, lastSpaceIndex);
                String durationString = request.substring(lastSpaceIndex + 1);
                if (durationString.equalsIgnoreCase(StringConstants.LIGHTNING)) {
                    durationMinutes = 5; // Assuming lightning talks are 5 minutes long
                } else {
                    durationMinutes = Integer.parseInt(durationString.replaceAll("\\D+", ""));
                }
            } else {
                // If no space is found, assume the entire string is the title
                title = request;
                durationMinutes = 0; // Set a default duration
            }

            eventList.add(new Event(title, durationMinutes));
        }
    }

    private void scheduleTasks() {
        scheduleByFirstFitDecreasing();
    }

    private void scheduleByFirstFitDecreasing() {
        Collections.sort(eventList);
        Collections.reverse(eventList);

        for (Event talk : eventList) {
            boolean isTalkOccupied = false;
            for (Track track : trackList) {
                if (track.addEvent(talk)) {
                    isTalkOccupied = true;
                    break;
                }
            }
            if (!isTalkOccupied) {
                Track track = new Track();
                track.addEvent(talk);
                trackList.add(track);
            }
        }

        for (Track track : trackList) {
            track.addNetworkingEvent();
        }
    }

    private List<String> printTracks() {
        List<String> output = new ArrayList<>();
        int trackCount = 1;
        for (Track track : trackList) {
            output.add("Track " + trackCount++ + ":");
            output.addAll(track.getSchedule());
        }
        return output;
    }
}

