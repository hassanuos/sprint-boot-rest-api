package com.journal.newmongoproject.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {


    //TODO
    // if any key is coming as _ then we need to tell the how to map in java
    // @JsonProperty("simple_coordinates")
    // public double simpleCoordinates;

    private Data data;

    @Getter
    @Setter
    public class Data{
        public String parameter;
    }
}


