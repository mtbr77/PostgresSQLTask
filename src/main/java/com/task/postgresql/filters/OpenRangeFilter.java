package com.task.postgresql.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenRangeFilter<T extends Comparable<T>> extends AbstractFilter {
    private T maxValue;
    @Override
    public boolean allows(String value) {
        return maxValue.compareTo((T) value) > 0;
    }
}
