package com.hf.eventstats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEvent {
    private Timestamp lastUpdate;
    private int[] countX;
    private int[] countY;
    private double[] sumX;
    private long[] sumY;
}
