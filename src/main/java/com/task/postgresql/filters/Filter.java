package com.task.postgresql.filters;

public interface Filter {
    boolean allows(String value);
    //boolean isUsed();
    String getName();
    void setName(String name);
}
