package com.gridnine.testing.filter;

import com.gridnine.testing.domain.Flight;
import com.gridnine.testing.domain.Segment;
import com.gridnine.testing.exception.NullDataException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * First implementation of the {@link Filterable} in this test project
 */
public class FlightFilter implements Filterable<Flight> {

    private final List<Flight> flights;

    public FlightFilter(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Returns a list with excluding items by {@link TimeArrow} pointer,
     * that set a follow in the timeline.
     *
     * @param pointer is a plaint enum that helps to set the orientation on the timeline
     * @param time    for compare with each date-time item of the flight
     * @return the new list
     */
    public List<Flight> filterFor(TimeArrow pointer, LocalDateTime time) {

        if (Objects.isNull(flights)) {
            throw new NullDataException("Input collection is null. Recheck your type.");
        }

        if (flights.isEmpty()) {
            return Collections.emptyList();
        }

        List<Flight> res = flights;

        switch (pointer) {
            case EARLIER_DEP_DATE -> res = filterEarlierDep(time);
            case LATER_DEP_DATE -> res = filterLaterDep(time);
            case EARLIER_ARR_DATE -> res = filterEarlierArr(time);
            case LATER_ARR_DATE -> res = filterLaterArr(time);
        }
        return res;
    }

    /**
     * Returns a list of items with the correct date-time pairs
     */
    @Override
    public List<Flight> filterIncorrectDates() {

        if (Objects.isNull(flights)) {
            throw new NullDataException("Input collection is null. Recheck your type.");
        }

        if (flights.isEmpty()) {
            return Collections.emptyList();
        }

        List<Flight> filteredFlight = new ArrayList<>();

        for (Flight flight : flights) {

            List<Segment> filtered = flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()))
                    .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                filteredFlight.add(new Flight(filtered));
            }
        }

        return filteredFlight;
    }

    /**
     * Returns a list with excluding items by {@link TimeArrow} summary time that presents by time
     *
     * @param time LocalDateTime item to calculation the filtering condition
     * @return the new list
     */
    @Override
    public List<Flight> filterSummaryTimeMoreThan(LocalTime time) {

        if (Objects.isNull(flights)) {
            throw new NullDataException("Input collection is null. Recheck your type.");
        }

        if (flights.isEmpty()) {
            return Collections.emptyList();
        }

        return flights
                .parallelStream()
                .filter(flight -> this
                        .isMoreThan(flight, time))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Flight> filterEarlierDep(LocalDateTime time) {

        List<Flight> filteredFlight = new ArrayList<>();

        for (Flight flight : flights) {

            List<Segment> filtered = flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getDepartureDate().isAfter(time))
                    .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                filteredFlight.add(new Flight(filtered));
            }
        }

        return filteredFlight;
    }

    private List<Flight> filterEarlierArr(LocalDateTime time) {

        List<Flight> filteredFlight = new ArrayList<>();

        for (Flight flight : flights) {

            List<Segment> filtered = flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getArrivalDate().isAfter(time))
                    .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                filteredFlight.add(new Flight(filtered));
            }
        }

        return filteredFlight;
    }

    private List<Flight> filterLaterDep(LocalDateTime time) {

        List<Flight> filteredFlight = new ArrayList<>();

        for (Flight flight : flights) {

            List<Segment> filtered = flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getDepartureDate().isBefore(time))
                    .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                filteredFlight.add(new Flight(filtered));
            }
        }

        return filteredFlight;
    }

    private List<Flight> filterLaterArr(LocalDateTime time) {

        List<Flight> filteredFlight = new ArrayList<>();

        for (Flight flight : flights) {

            List<Segment> filtered = flight.getSegments()
                    .stream()
                    .filter(segment -> segment.getArrivalDate().isBefore(time))
                    .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                filteredFlight.add(new Flight(filtered));
            }
        }

        return filteredFlight;
    }


    boolean isMoreThan(Flight flight, LocalTime localTime) {

        List<Segment> segments = flight.getSegments();
        List<Duration> durations = new ArrayList<>();

        for (int i = 0; i < segments.size() - 1; i++) {
            Segment current = segments.get(i);
            Segment next = segments.get(i + 1);
            durations.add(Duration.between(current.getArrivalDate(), next.getDepartureDate()).abs());
        }

        long summaryTime = durations.stream()
                .map(Duration::toSeconds)
                .reduce(Long::sum)
                .orElse(0L);

        return summaryTime < localTime.toSecondOfDay();
    }
}