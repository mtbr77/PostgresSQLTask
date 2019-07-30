package com.task.postgresql.filters;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

@Getter
public class FiltersService {

    private Map<String,Filter> filters = new HashMap<>();

    public FiltersService() {
        Properties properties = new Properties();
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            BiConsumer propertyProcessor = (k, v) -> {
                String propertyName = (String)k;
                String propertyValue = (String)v;
                if(propertyName.startsWith("filter")) {
                    processFilterProperty(propertyName, propertyValue);
                }
            };
            properties.forEach(propertyProcessor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFilterProperty(String propertyName, String propertyValue) {
        String filterName = propertyName.split("\\.")[1];
        String valueName = propertyName.split("\\.")[2];
        Filter filter = null;
        if(valueName.equals("value")) {
            filter = new ListFilter(propertyValue);
            filter.setName(filterName);
        }
        else
        if(valueName.equals("min")) {
            if(!filters.containsKey(filterName)) {
                filter = new RangeFilter(propertyValue,null);
                filter.setName(filterName);
            } else ((RangeFilter)filters.get(filterName)).setMinValue(propertyValue);

        }
        else
        if(valueName.equals("max")) {
            if(!filters.containsKey(filterName)) {
                filter = new RangeFilter(null,propertyValue);
                filter.setName(filterName);
            } else ((RangeFilter)filters.get(filterName)).setMaxValue(propertyValue);
        }

        if(filter!=null) filters.put(filterName, filter);
    }
}
