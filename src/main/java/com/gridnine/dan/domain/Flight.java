package com.gridnine.dan.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
public class Flight implements Comparable<Flight> {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private final long id;
    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        id = ID_GENERATOR.incrementAndGet();
        segments = segs;
    }

    List<Segment> getSegments() {
        return segments;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Flight №" + id + " = " + segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public int compareTo(Flight o) {
        return Long.compare(this.id, o.id);
    }
}
