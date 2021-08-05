package utils;

import dao.BaseFake;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = new Properties() ;

    static {
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(BaseFake.class.getProtectionDomain().getClassLoader().getResource("client.properties").getPath());
            properties.load(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Properties getProperties() {
        return properties;
    }
}
