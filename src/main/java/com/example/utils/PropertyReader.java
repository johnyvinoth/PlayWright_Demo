package com.example.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private Properties properties;

    public PropertyReader() {
        properties=new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                System.out.println("Sorry, Unable to find config.properties file");
                return;
            }
            properties.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

}
