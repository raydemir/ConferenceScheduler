package com.example.conferencescheduler.service;

import com.example.conferencescheduler.model.Event;
import com.example.conferencescheduler.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ConferenceServiceImplTest {


    @Mock
    private Event eventMock;

    @Mock
    private Track trackMock;

    @InjectMocks
    private ConferenceServiceImpl conferenceService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }

    @Test
    public void testSchedulePresentations() {
        // Arrange
        List<String> presentationRequests = Arrays.asList(
                "Architecting Your Codebase 60min",
                "Overdoing it in Python 45min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "JUnit 5 - Shaping the Future of Testing on the JVM 45min",
                "Flavors of Concurrency in Java 30min",
                "Cloud Native Java lightning",
                "Communicating Over Distance 60min",
                "AWS Technical Essentials 45min",
                "Continuous Delivery 30min",
                "Monitoring Reactive Applications 30min",
                "Pair Programming vs Noise 45min",
                "Rails Magic 60min",
                "Microservices \"Just Right\" 60min",
                "Clojure Ate Scala (on my project) 45min",
                "Perfect Scalability 30min",
                "Apache Spark 30min",
                "Async Testing on JVM 60min",
                "A World Without HackerNews 30min",
                "User Interface CSS in Apps 30min"
        );

        when(eventMock.getDuration()).thenReturn(60); // Mocking event duration
        when(trackMock.addEvent(any())).thenReturn(true); // Mocking track to add event successfully

        // Act
        List<String> result = conferenceService.schedulePresentations(presentationRequests);

        // Assert
        assertEquals(presentationRequests.size() + 6, result.size()); // Assuming two tracks, two lunch times and two networking event are generated.
    }
}

