package com.task.postgresql.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFilter implements Filter{
    //private boolean used;
    private String name;
}
