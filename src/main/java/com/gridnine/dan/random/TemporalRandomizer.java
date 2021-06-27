package com.gridnine.dan.random;

import java.time.temporal.Temporal;

public interface TemporalRandomizer<T extends Temporal> {

    T[] create();
}
