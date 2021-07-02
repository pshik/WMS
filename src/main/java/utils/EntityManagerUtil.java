package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


public class EntityManagerUtil {
    @PersistenceContext
    private static final EntityManager entityManager;
    private static final EntityManagerFactory factory;

    private EntityManagerUtil() {
    }

    static {
        try{
            factory = Persistence.createEntityManagerFactory("GA");
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
