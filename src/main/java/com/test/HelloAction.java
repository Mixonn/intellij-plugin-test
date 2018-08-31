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
            String temperature = Integer.toString(WeatherReader.checkTemperature(temperatureScale));
            Messages.showMessageDialog(project,
                    "The temperature in Poznań is " + temperature + "degrees " + temperatureScale.toString().toLowerCase(),
                    "Greeting",
                    Messages.getInformationIcon());
        } catch (IOException e) {
            Messages.showMessageDialog(project, "Cannot download temperature", "Error", Messages.getErrorIcon());
        }
    }
}