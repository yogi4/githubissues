package com.alienvault.utilities;

import java.io.IOException;
import java.io.InputStream;import com.alienvault.Main;

import java.util.Properties;
public class GitIssuesProperties {

    public GitIssuesProperties() {

        loadProperties();
    }

    /**
     * Load Properties
     */
    public static Properties loadProperties()
    {
        Properties properties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            properties.load(inputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return properties;
    }
}
