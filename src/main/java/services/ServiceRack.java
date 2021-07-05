package services;

import metamodels.Rack_;
import models.Rack;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServiceRack {
    public ServiceRack() {
    }
    public Rack getRackByID(long id){
        Rack rack = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            rack = entityManager.find(Rack.class, id);
            entityManager.detach(rack);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return rack;
    }

    @SuppressWarnings("unchecked")
    public List<Rack> getRacks(){
        List<Rack> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            list = entityManager.createQuery("from Rack").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (Rack rack){

        EntityManager entityManager = null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(rack);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void removeRackByID(long id){

    }
    public Rack getRackByName(String name){
        Rack rack = null;
        EntityManager entityManager=null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Rack> criteria = criteriaBuilder.createQuery(Rack.class);
            Root<Rack> root = criteria.from(Rack.class);
            criteria.where(criteriaBuilder.equal(root.get(Rack_.name), name));
            TypedQuery<Rack> typed = entityManager.createQuery(criteria);

            rack = typed.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return rack;
    }
}
