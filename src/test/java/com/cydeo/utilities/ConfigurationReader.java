package com.cydeo.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties = new Properties();


    public static String getProperty (String key) {

        return properties.getProperty(key);

    }

    static {


        try {

            FileInputStream fileInputStream = new FileInputStream("configuration.properties");

            properties.load(fileInputStream);

            fileInputStream.close();


        } catch (IOException exception) {

            System.out.println("Invalid file name");

            exception.printStackTrace();

        }


    }









}
