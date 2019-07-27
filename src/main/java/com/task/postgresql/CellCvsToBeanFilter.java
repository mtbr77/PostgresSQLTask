package com.task.postgresql;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.task.postgresql.filters.Filter;
import com.task.postgresql.filters.FiltersService;
import com.task.postgresql.model.Cell;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class CellCvsToBeanFilter implements CsvToBeanFilter {
    private final HeaderColumnNameTranslateMappingStrategy<Cell> strategy = new HeaderColumnNameTranslateMappingStrategy<>();

    public CellCvsToBeanFilter(CSVReader reader) throws IOException {
        try {
            strategy.captureHeader(reader);
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private FiltersService filtersService;

    public boolean allowLine(String[] line) {
        for(Filter filter : filtersService.getFilters().values()) {
            int index = strategy.getColumnIndex(filter.getName());
            String value = line[index];
            if (!filter.allows(value)) return false;
        }
        return true;
    }
}
