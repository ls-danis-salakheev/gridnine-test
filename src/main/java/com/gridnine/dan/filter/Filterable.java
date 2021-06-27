package com.gridnine.dan.filter;

import java.time.LocalDateTime;

public interface Filterable{

    boolean filterByTime(LocalDateTime localDateTime);

    static boolean beforeNow(LocalDateTime localDateTime) {
        return localDateTime.compareTo(LocalDateTime.now()) < 0;
    }

}
