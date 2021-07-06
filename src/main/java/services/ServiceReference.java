package services;

import metamodels.SapReference_;
import models.SapReference;
import models.User;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServiceReference {
    public ServiceReference() {
    }
    public SapReference getReferenceByID(long id){
        SapReference sapReference = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            sapReference = entityManager.find(SapReference.class, id);
            entityManager.detach(sapReference);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return sapReference;
    }

    @SuppressWarnings("unchecked")
    public List<SapReference> getReferences(){
        List<SapReference> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            list = entityManager.createQuery("from SapReference").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (SapReference sapReference){

        EntityManager entityManager = null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(sapReference);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void removeReferenceByID(long id){

    }
    public SapReference getReferenceByName(String field){
        SapReference sapReference = null;
        EntityManager entityManager=null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<SapReference> criteria = criteriaBuilder.createQuery(SapReference.class);
            Root<SapReference> root = criteria.from(SapReference.class);
            criteria.where(criteriaBuilder.equal(root.get(SapReference_.reference), field));
            TypedQuery<SapReference> typed = entityManager.createQuery(criteria);

            sapReference = typed.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return sapReference;
    }

    public void removeByID(Long id) {
        EntityManager entityManager=null;
        try
        {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            SapReference sapReference = getReferenceByID(id);
            entityManager.remove(sapReference);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
