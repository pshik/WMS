package services;

import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ServiceHibernate {
    public ServiceHibernate() {
    }

    public  void save (Object obj){
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.save(obj);
        transaction.commit();

    }

    @SuppressWarnings("unchecked")
    public Object getObjectByField(Class theClass,String value,String field){
        Object obj = null;
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();

        Query<User> query = session.createQuery("from " + theClass.getName() + " o where o." + field + "=:" + field, theClass);
        query.setParameter(field, value);
        obj = query.uniqueResult();

        transaction.commit();
        return obj;
    }

    public  <T> List<T> getAllData(Class<T> type) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        transaction.commit();
        return data;
    }

    public Object getObjectById(Class theClass, long id){
        Object obj = null;
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        obj = session.load(theClass, id);
        transaction.commit();
        return obj;
    }

    public void removeByID(Class theClass,Long id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        Object ent = session.load(theClass, id);
        session.delete(ent);
        transaction.commit();
    }

    public void update(Object obj) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
    }
}
