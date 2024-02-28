package com.example.conferencescheduler.controller;

import com.example.conferencescheduler.service.ConferenceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class responsible for handling requests related to conference scheduling.
 */
@RestController
public class ConferenceController {

    private final ConferenceService conferenceService;

    /**
     * Constructor to initialize the ConferenceController with a ConferenceService.
     *
     * @param conferenceService The service responsible for conference scheduling operations.
     */
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    /**
     * Endpoint to schedule presentations for a conference.
     *
     * @param presentationRequests A list of strings representing presentation requests.
     * @return A list of strings representing the scheduled presentations.
     */
    @PostMapping("/schedule")
    public List<String> schedule(@RequestBody List<String> presentationRequests) {
        return conferenceService.schedulePresentations(presentationRequests);
    }
}

