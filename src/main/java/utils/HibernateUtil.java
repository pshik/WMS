package utils;

import dao.BaseFake;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static String  serverAddress = "localhost";
    private static String serverPort = "5432";
    private static String dbName ="GA";

    private static SessionFactory sessionFactory = null;
    static {
        if (BaseFake.getProperties() != null) {
            serverAddress = PropertiesUtil.getProperties().getProperty("db_server.ip");
            serverPort = PropertiesUtil.getProperties().getProperty("db_server.port");
            dbName = PropertiesUtil.getProperties().getProperty("db_server.name");
        }

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        cfg.setProperty("hibernate.connection.url","jdbc:postgresql://" + serverAddress + ":" + serverPort + "/" + dbName);
      //  jdbc:postgresql://localhost:5432/phonesdemo
        //cfg.setProperty("hibernate.connection.url","jdbc:hsqldb:hsql://" + serverAddress + ":" + serverPort + "/" + dbName);

        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession() {

        Session session = null;
        if (threadLocal.get() == null) {
            // Create Session object
            session = sessionFactory.openSession();
            threadLocal.set(session);
        } else {
            session = threadLocal.get();
        }

        return session;
    }

    public static void closeSession() {
        Session session = null;
        if (threadLocal.get() != null) {
            session = threadLocal.get();
            session.close();
            threadLocal.remove();
        }
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
