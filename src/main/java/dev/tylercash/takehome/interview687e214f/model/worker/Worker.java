package dev.tylercash.takehome.interview687e214f.model.worker;

import java.util.List;

import lombok.Data;

@Data
public class Worker {
    private int rating;
    private Boolean isActive;
    private List<String> certificates;
    private List<String> skills;
    private JobSearchAddress jobSearchAddress;
    private Transportation transportation;
    private boolean hasDriversLicense;
    private List<Availability> availability;
    private String phone;
    private String email;
    private FullName name;
    private int age;
    private String guid;
    private int userId;
}
