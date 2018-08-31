package com.test;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;

public class HelloAction extends AnAction {

    private TemperatureScale temperatureScale = TemperatureScale.CELSIUS;

    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        try {
            WeatherReader weatherReader = new WeatherReader();
            Weather weather = weatherReader.getWeather(temperatureScale);

            String temperature = Integer.toString(weather.getTemperature().getAmount());
            String textWeather = weather.getTextWeather();
            Messages.showMessageDialog(project,
                    "The amount in Poznań is " + temperature + " degrees " + temperatureScale.toString().toLowerCase() +
                    "\r\n Today is " + textWeather,
                    "Greeting",
                    Messages.getInformationIcon());
        } catch (IOException e) {
            Messages.showMessageDialog(project, "Cannot download amount", "Error", Messages.getErrorIcon());
        }
    }
}