package com.test;

import org.json.JSONObject;

class WeatherReader {
    private static final String URL = "https://query.yahooapis.com/v1/public/yql?" +
            "q=select%20item.condition.temp%20" +
            "from%20weather.forecast%20" +
            "where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Poznan%2C%20pl%22)" +
            "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    private JSONObject query;

}
