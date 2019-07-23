package com.task.postgresql.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cell {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @CsvBindByName
    private String radio;

    @CsvBindByName
    private int mcc;

    @CsvBindByName
    int net;

    @CsvBindByName
    int area;

    @CsvBindByName
    int cell;

    @CsvBindByName
    int unit;

    @CsvBindByName
    double lon;

    @CsvBindByName
    double lat;

    @CsvBindByName
    int range;

    @CsvBindByName
    int samples;

    @CsvBindByName
    int changeable;

    @CsvBindByName
    int created;

    @CsvBindByName
    long updated;

    @CsvBindByName
    int averageSignal;

}
