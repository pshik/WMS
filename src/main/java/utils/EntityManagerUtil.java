package utils;

import dao.BaseFake;
import views.GUI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class EntityManagerUtil {
    @PersistenceContext
    private static final EntityManager entityManager;
    private static final EntityManagerFactory factory;

    private EntityManagerUtil() {
    }

    static {
        try{
            String serverAddress = "localhost";
            String serverPort = "9001";
            String dbName = "test";

            if(BaseFake.getProperties() != null) {
                serverAddress = PropertiesUtil.getProperties().getProperty("db_server.ip");
                serverPort = PropertiesUtil.getProperties().getProperty("db_server.port");
                dbName = PropertiesUtil.getProperties().getProperty("db_server.name");
            }

            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:hsql://" + serverAddress + ":" + serverPort + "/" + dbName);
            factory = Persistence.createEntityManagerFactory("GA", properties);
            entityManager = factory.createEntityManager();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." );
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {return entityManager;}

    public static void shutdown(){
        getEntityManager().close();
        factory.close();
    }
}
