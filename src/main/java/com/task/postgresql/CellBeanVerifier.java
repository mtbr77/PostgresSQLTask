package com.task.postgresql;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.task.postgresql.model.Cell;

public class CellBeanVerifier implements BeanVerifier<Cell> {
    @Override
    public boolean verifyBean(Cell cell) throws CsvConstraintViolationException {

        return false;
    }
}
