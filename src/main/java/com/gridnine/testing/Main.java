package com.gridnine.testing;

import com.gridnine.testing.domain.Flight;
import com.gridnine.testing.filter.Filterable;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.TimeArrow;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.gridnine.testing.FlightBuilder.createFlights;

/**
 * @version 0.1
 * Runner-Class
 */
public class Main {

    public static void main(String[] args) {

        List<Flight> flights = createFlights();

        Filterable<Flight> segmentFilter = new FlightFilter(flights);

        //test #1
        segmentFilter
                .filterFor(TimeArrow.EARLIER_DEP_DATE, LocalDateTime.now())
                .forEach(System.out::println);


        //task #2
        System.out.println("\nTask #2: Excluded flights which arrival time is earlier than departure time:");
        segmentFilter
                .filterIncorrectDates()
                .forEach(System.out::println);

        //task #3
        System.out.println("\nTask #3: Exclude all flights with time more than 2 hours in the airport:");
        segmentFilter
                .filterSummaryTimeMoreThan(LocalTime.of(2, 0))
                .forEach(System.out::println);

    }
}