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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class EntityManagerUtil {
    @PersistenceContext
    private static final EntityManager entityManager;
    private static final EntityManagerFactory factory;
    private static String  serverAddress = "localhost";
    private static String serverPort = "9001";
    private static String dbName ="test";
    private EntityManagerUtil() {
    }

    static {
        try {

            if (BaseFake.getProperties() != null) {
                serverAddress = PropertiesUtil.getProperties().getProperty("db_server.ip");
                serverPort = PropertiesUtil.getProperties().getProperty("db_server.port");
                dbName = PropertiesUtil.getProperties().getProperty("db_server.name");
            }

            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:hsql://" + serverAddress + ":" + serverPort + "/" + dbName);

                factory = Persistence.createEntityManagerFactory("GA", properties);
                entityManager = factory.createEntityManager();

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed.");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {return entityManager;}

    public static void shutdown(){
        getEntityManager().close();
        factory.close();
    }

//    private static boolean testDB(){
//        try{
//            Class.forName("org.hsqldb.jdbcDriver");
//            Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://" + serverAddress + ":" + serverPort + "/" + dbName,"sa","");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from emp");
//            conn.close();
//            return true;
//        }catch(Exception ex){
//            System.out.println(ex);
//        }
//        return false;
//    }
}
