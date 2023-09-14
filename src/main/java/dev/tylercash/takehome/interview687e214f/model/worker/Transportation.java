package dev.tylercash.takehome.interview687e214f.model.worker;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Transportation {
    CAR,
    @JsonProperty("PUBLIC TRANSPORT")
    PUBLIC_TRANSPORT;
}
