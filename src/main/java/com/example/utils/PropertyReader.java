package com.example.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private Properties properties;

    public PropertyReader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, Unable to find config.properties file");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public getProperty(String key)
    {
        return properties.getProperty(key);
    }

}
