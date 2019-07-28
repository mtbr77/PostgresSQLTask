package com.task.postgresql.filters;

public class RangeFilter extends AbstractFilter {
    private Number minValue, maxValue;

    private Number convert(String s) {
        if(s.contains(".")) return new Double(s); else return new Long(s);
    }

    public RangeFilter(String minValue, String maxValue) {
        if(minValue!=null) setMinValue(minValue);
        if(maxValue!=null) setMaxValue(maxValue);
    }

    public void setMinValue(String minValue) {
        this.minValue = convert(minValue);
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = convert(maxValue);
    }

    @Override
    public boolean allows(String value) {
        Number numberValue = convert(value);

        return minValue==null ? true : ((Comparable<Number>)minValue).compareTo(numberValue) < 0
               &&
               maxValue==null ? true : ((Comparable<Number>)maxValue).compareTo(numberValue) > 0;
    }
}
