package com.gridnine.dan;

import com.gridnine.dan.domain.Flight;
import com.gridnine.dan.domain.Segment;
import com.gridnine.dan.random.DateTimeRandomizer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Factory class to get big random list of flights.
 */
public class BigFlightBuilder {

    private static final Random RANDOM = new Random();

    private static final DateTimeRandomizer dtRandom = new DateTimeRandomizer();


    static List<Flight> createFlights() {

        int count = RANDOM.nextInt(100000);

        List<Flight> flightsList = new ArrayList<>(Math.abs(count));

        for (int i = 0; i < count; i++) {
            flightsList.add(createFlight(dtRandom.create()));
        }

        return flightsList;

    }

    private static Flight createFlight(final LocalDateTime[] dates) {

        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

}
