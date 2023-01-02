package com.hf.eventstats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stats {
    private int total;
    private double sumX;
    private double avgX;
    private long sumY;
    private double avgY;
}
