package com.test;

import com.test.util.json.JsonReader;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherReader {
    public Weather getWeather(TemperatureScale scale) throws IOException {
        Weather weather = new Weather();
        JSONObject weatherInfo = unpackWeatherJson(JsonReader.readJsonFromUrl(generateUrl("Poznan", "pl")));
        weather.setTemperature(getTemperature(weatherInfo, scale));
        weather.setTextWeather(getTextWeather(weatherInfo));
        return weather;
    }

    public Weather getWeather(TemperatureScale scale, String city, String country) throws IOException {
        Weather weather = new Weather();
        weather.setCity(city);
        weather.setCountry(country);

        JSONObject weatherInfo = unpackWeatherJson(JsonReader.readJsonFromUrl(generateUrl(city, country)));
        weather.setTemperature(getTemperature(weatherInfo, scale));
        weather.setTextWeather(getTextWeather(weatherInfo));
        return weather;
    }

    private String getTextWeather(JSONObject weatherInfo) {
        return weatherInfo.getString("text");
    }

    private Temperature getTemperature(JSONObject weatherInfo,TemperatureScale scale) throws IOException {
        int fahrenheitTemp = weatherInfo.getInt("temp");

        Temperature temperature = new Temperature();
        temperature.setTemperatureScale(scale);
        switch (scale) {
            case CELSIUS:
                temperature.setAmount(fahrenheitToCelsius(fahrenheitTemp));
                break;
            case FAHRENHEIT:
                temperature.setAmount(fahrenheitTemp);
                break;
        }
        return temperature;
    }

    private JSONObject unpackWeatherJson(JSONObject jsonObject){
        JSONObject query = jsonObject.getJSONObject("query");
        JSONObject results = query.getJSONObject("results");
        JSONObject channel = results.getJSONObject("channel");
        JSONObject item = channel.getJSONObject("item");
        return item.getJSONObject("condition");
    }

    private int fahrenheitToCelsius(int fahrenheitTemp) {
        return (int) ((fahrenheitTemp-32)*(5.0/9.0));
    }

    private String generateUrl(String city, String twoLettersNationality){
        StringBuilder builder = new StringBuilder();
        builder.append("https://query.yahooapis.com/v1/public/yql?q=" +
                "select%20item.condition%20" +
                "from%20weather.forecast%20" +
                "where%20woeid%20in%20" +
                "(select%20woeid%20" +
                "from%20geo.places(1)%20" +
                "where%20text%3D%22");
        builder.append(city);
        builder.append("%2C%20");
        builder.append(twoLettersNationality);
        builder.append("%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
        return builder.toString();
    }

}
