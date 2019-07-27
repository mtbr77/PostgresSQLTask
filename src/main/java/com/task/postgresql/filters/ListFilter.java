package com.task.postgresql.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListFilter<T> extends AbstractFilter {
    private T value;
    @Override
    public boolean allows(String value) {
        return this.value.equals(value);
    }
}
