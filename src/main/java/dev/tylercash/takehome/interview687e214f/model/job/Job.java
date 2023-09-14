package dev.tylercash.takehome.interview687e214f.model.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.tylercash.takehome.interview687e214f.model.deserializer.BillRateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Job {
    private boolean driverLicenseRequired;
    private List<String> requiredCertificates;
    private Location location;
    @JsonDeserialize(using = BillRateDeserializer.class)
    private BigDecimal billRate;
    private int workersRequired ;
    private ZonedDateTime startDate ;
    private String about ;
    private String jobTitle ;
    private String company ;
    private String guid ;
    private int jobId ;

}
