package services;

import models.Reference;
import models.Reference_;
import models.User;
import models.User_;
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
    public Reference getReferenceByID(long id){
        Reference reference = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            reference = entityManager.find(Reference.class, id);
            entityManager.detach(reference);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return reference;
    }

    @SuppressWarnings("unchecked")
    public List<Reference> getReferences(){
        List<Reference> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            list = entityManager.createQuery("from Reference").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (Reference reference){

        EntityManager entityManager = null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(reference);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void removeReferenceByID(long id){

    }
    public Reference getUserByReference(String field){
        Reference reference = null;
        EntityManager entityManager=null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Reference> criteria = criteriaBuilder.createQuery(Reference.class);
            Root<Reference> root = criteria.from(Reference.class);
            criteria.where(criteriaBuilder.equal(root.get(Reference_.reference), field));
            TypedQuery<Reference> typed = entityManager.createQuery(criteria);

            reference = typed.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return reference;
    }
}
