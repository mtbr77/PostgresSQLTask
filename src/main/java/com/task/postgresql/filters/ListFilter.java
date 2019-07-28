package com.task.postgresql.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListFilter extends AbstractFilter {
    private String value;

    public ListFilter(String value) {
        this.value = value;
    }

    @Override
    public boolean allows(String value) {
        return this.value.equals(value);
    }
}
