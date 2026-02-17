package Utitlity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigTestData {
    Properties properties;
    private final String filePath= "testData.properties";

    // Constructor
    public ConfigTestData()
    {
        // Creating File object
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + filePath);
        }
    }
    // Methods to read data from config.properties

    public String setDriverPath(){
        String driverPath = properties.getProperty("chromePath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public String setBaseURL() { // ok - add new url in config.properties
        String url=properties.getProperty("baseUrl");
        return url;
    }

    public String setBrowser() { // ok - add new url in config.properties
        String browser =properties.getProperty("browser");
        return browser;
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }
}
