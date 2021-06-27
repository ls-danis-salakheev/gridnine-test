package com.gridnine.dan.filter;

import java.util.List;

public abstract class Filter<T> {

    public abstract List<T> filterByTime(Filterable filter);

}
