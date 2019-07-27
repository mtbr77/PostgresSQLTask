package com.task.postgresql.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RangeFilter<T extends Comparable<T>> extends OpenRangeFilter<T> {
    private T minValue;
    @Override
    public boolean allows(String value) {
        return super.allows(value) && minValue.compareTo((T) value) < 0;
    }
}
