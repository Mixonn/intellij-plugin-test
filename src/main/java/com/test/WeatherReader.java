package com.test;

import org.json.JSONObject;

import java.io.IOException;

class WeatherReader {
    private static final String URL = "https://query.yahooapis.com/v1/public/yql?" +
            "q=select%20item.condition.temp%20" +
            "from%20weather.forecast%20" +
            "where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Poznan%2C%20pl%22)" +
            "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    public static int checkTemperature(TemperatureScale scale) throws IOException {
        JSONObject jsonObject = JsonReader.readJsonFromUrl(URL);
        JSONObject query = jsonObject.getJSONObject("query");
        JSONObject results = query.getJSONObject("results");
        JSONObject channel = results.getJSONObject("channel");
        JSONObject item = channel.getJSONObject("item");
        JSONObject condition = item.getJSONObject("condition");
        int farenheitTemp = condition.getInt("temp");
        switch (scale) {
            case CELSIUS:
                return fahrenheitToCelsius(farenheitTemp);
            case FAHRENHEIT:
                return farenheitTemp;
        }
        throw new IllegalArgumentException();
    }

    private static int fahrenheitToCelsius(int farenheitTemp) {
        return (int) ((farenheitTemp-32)*(5.0/9.0));
    }

}
