package com.task.postgresql;

import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.task.postgresql.filters.Filter;
import com.task.postgresql.filters.FiltersService;
import com.task.postgresql.model.Cell;

import java.io.IOException;

public class CvsToBeanFilterImpl implements CsvToBeanFilter {
    private MappingStrategy<Cell> strategy = null;

    public CvsToBeanFilterImpl(MappingStrategy<Cell> strategy) throws IOException {
        this.strategy = strategy;
    }

    private FiltersService filtersService = new FiltersService();

    public boolean allowLine(String[] line) {
        for(Filter filter : filtersService.getFilters().values()) {
            int index = strategy.getColumnIndex(filter.getName());
            String value = line[index];
            if (!filter.allows(value)) return false;
        }
        return true;
    }
}
