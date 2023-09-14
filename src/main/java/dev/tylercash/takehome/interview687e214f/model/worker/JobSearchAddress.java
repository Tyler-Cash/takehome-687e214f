package dev.tylercash.takehome.interview687e214f.model.worker;

import dev.tylercash.takehome.interview687e214f.model.job.DistanceUnit;
import lombok.Data;

@Data
public class JobSearchAddress {
    private DistanceUnit unit;
    private int maxJobDistance;
    private double longitude;
    private double latitude;
}
