package com.example.conferencescheduler.service;

import java.util.List;

public interface ConferenceService {
    List<String> schedulePresentations(List<String> presentations);
}