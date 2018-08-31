package com.test.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.test.TemperatureScale;
import com.test.Weather;
import com.test.WeatherReader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

public class WindowTool implements ToolWindowFactory{
    private JPanel panel1;
    private JTextField cityName;
    private JButton getWeatherButton;
    private JLabel theActualWeather;
    private JTextField nationality;
    private ToolWindow myToolWindow;

    private WeatherReader weatherReader;

    public WindowTool(){
        weatherReader = new WeatherReader();

        StringBuilder stringBuilder = new StringBuilder();

        getWeatherButton.addActionListener(e -> {
            String city = cityName.getText();
            String country = nationality.getText();
            try {
                Weather weather = weatherReader.getWeather(TemperatureScale.CELSIUS, city, country);
                theActualWeather.setText(generateMessageFromWeather(weather));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel1, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void init(ToolWindow window) {

    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return false;
    }

    private String generateMessageFromWeather(Weather weather){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The amount in ")
                .append(weather.getCity())
                .append("(")
                .append(weather.getCountry())
                .append(") is ")
                .append(weather.getTemperature().getAmount())
                .append(" degrees ")
                .append(weather.getTemperature().getTemperatureScale().toString().toLowerCase());
        return stringBuilder.toString();
    }
}
