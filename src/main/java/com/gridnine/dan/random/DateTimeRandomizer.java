package com.gridnine.dan.random;

import java.time.LocalDateTime;
import java.util.Random;

public class DateTimeRandomizer implements TemporalRandomizer<LocalDateTime> {

    private static final LocalDateTime now = LocalDateTime.now();

    private static final Random RANDOM = new Random();

    public LocalDateTime getRandomDate() {

        int selector = RANDOM.nextInt(3);
        int hOffset = RANDOM.nextInt(365);

        LocalDateTime returnedDT = now;

        if (selector == 0) return returnedDT;
        if (selector == 1) {
            returnedDT = returnedDT.minusDays(hOffset);
            returnedDT = offsetRandomizer(returnedDT);
        }
        if (selector > 1) {
            returnedDT = returnedDT.plusDays(hOffset);
            returnedDT = offsetRandomizer(returnedDT);
        }

        return returnedDT;
    }

    @Override
    public LocalDateTime[] create() {

        int datesCount;
        do {
            datesCount = RANDOM.nextInt(15);
        }
        while ((datesCount % 2) != 0);

        LocalDateTime[] localDateTimes = new LocalDateTime[datesCount];

        for (int i = 0; i < localDateTimes.length; i++) {
            localDateTimes[i] = getRandomDate();
        }

        return localDateTimes;
    }

    private LocalDateTime offsetRandomizer(final LocalDateTime returned) {
        final int HOURS = 23;
        final int SEC_MS = 59;

        return returned
                .plusHours(RANDOM.nextInt(HOURS) + 1)
                .minusHours(RANDOM.nextInt(HOURS) + 1)
                .plusMinutes(RANDOM.nextInt(SEC_MS) + 1)
                .minusMinutes(RANDOM.nextInt(SEC_MS) + 1)
                .plusSeconds(RANDOM.nextInt(59) + 1)
                .minusSeconds(RANDOM.nextInt(SEC_MS) + 1);
    }

}
