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
    private int net;

    @CsvBindByName
    private int area;

    @CsvBindByName
    private int cell;

    @CsvBindByName
    private int unit;

    @CsvBindByName
    private double lon;

    @CsvBindByName
    private double lat;

    @CsvBindByName
    private int range;

    @CsvBindByName
    private int samples;

    @CsvBindByName
    private int changeable;

    @CsvBindByName
    private int created;

    @CsvBindByName
    private long updated;

    @CsvBindByName
    private int averageSignal;

}
