package com.epam.rest.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static final String PROPERTIES_FILE_PATH = "src\\main\\resources\\httpserver.properties";
    private static Properties prop = new Properties();

    static {
        FileInputStream input = null;
        File file;
        try {
            file = new File(PROPERTIES_FILE_PATH);
            System.out.println(file.getAbsoluteFile());
            input = new FileInputStream(file);
            prop.load(input);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public static String getProperty(String prop_name) {
        return prop.getProperty(prop_name);
    }
}
