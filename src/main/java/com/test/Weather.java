package com.test;

import lombok.Data;

@Data
public class Weather {
    private Temperature temperature;
    private String textWeather;
    private String city;
    private String country;
}
