package com.org.utils;

import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class configReader {
    public static Properties prop;
    private static final Logger logger = LoggerHelper.getLogger(configReader.class);

    public static String getProperty(String key){
        try{
            prop = new Properties();
            FileInputStream file = new FileInputStream("./config/config.properties");
            prop.load(file);
        }catch(Exception e){
            logger.info("Could not load Properties file");
        }

        return prop.getProperty(key);
    }
}
