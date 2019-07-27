package com.task.postgresql.filters;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

@Getter
@Component
public class FiltersService {

    private Map<String,Filter> filters = new HashMap<>();

    public FiltersService() {
        Properties properties = new Properties();
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            BiConsumer biConsumer = (k, v) -> {
                String propertyName = (String)k;
                String propertyValue = (String)v;
                if(propertyName.startsWith("filter")) {
                    String filterName = propertyName.split("\\.")[1];

                    if(propertyName.endsWith("type")) {
                        String filterClassName = "com.task.postgresql.filters." + propertyValue + "Filter";
                        try {
                            Class filterClass = Class.forName(filterClassName);
                            Filter filter = (Filter) filterClass.newInstance();
                            filter.setName(filterName);
                            filters.put(filterName, filter);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(propertyName.endsWith("max")) ((OpenRangeFilter)filters.get(propertyName)).setMaxValue(propertyValue);
                    else if(propertyName.endsWith("min")) ((RangeFilter)filters.get(propertyName)).setMinValue(propertyValue);
                    else ((ListFilter)filters.get(propertyName)).setValue(propertyValue);
                }


            };
            properties.forEach(biConsumer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
