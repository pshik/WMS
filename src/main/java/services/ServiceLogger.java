package services;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Marker;

import java.io.File;

public class ServiceLogger {
    private static Logger logger ;

    public ServiceLogger(Class theClass) {
        logger = LoggerFactory.getLogger(theClass);
        String path = theClass.getProtectionDomain().getClassLoader().getResource("logs/log4j.properties").getPath();
        PropertyConfigurator.configure(path);
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File(path);
        context.setConfigLocation(file.toURI());
        logger.info("GUI is started!");
    }

    public static void writeInfoLog(Class theClass,String message){
        logger.info(theClass.getName() + "_" + message);
    }

    public static void writeErrorLog(Class theClass,String message) {
        logger.info(theClass.getName() + "_" + message);
    }
}
