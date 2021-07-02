package services;

import models.User;
import metamodels.User_;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServiceUser {
    public ServiceUser() {
    }

    public User getUserByID(long id){
        User user = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.detach(user);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers(){
        List<User> list = null;
        EntityManager entityManager = null;
        try {
        entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();

        list = entityManager.createQuery("from User").getResultList();
        entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (User user){

        EntityManager entityManager = null;
        try{
        entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(user);
        entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void removeUserByID(long id){

    }
    public User getUserByUsername(String username){
        User user = null;
        EntityManager entityManager=null;
        try{
        entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.where(criteriaBuilder.equal(root.get(User_.username), username));
        TypedQuery<User> typed = entityManager.createQuery(criteria);

        user = typed.getSingleResult();
        entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return user;
    }

}
