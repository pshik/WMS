package services;

import metamodels.Pallet_;
import models.Pallet;
import models.User;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServicePallet {
    public ServicePallet() {
    }
    public Pallet getPalletByID(long id){
        Pallet pallet = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            pallet = entityManager.find(Pallet.class, id);
            entityManager.detach(pallet);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return pallet;
    }

    @SuppressWarnings("unchecked")
    public List<Pallet> getPallets(){
        List<Pallet> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            list = entityManager.createQuery("from Pallet").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (Pallet pallet){

        EntityManager entityManager = null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(pallet);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void removePalletByID(long id){

    }
    public Pallet getPalletByMaterial(String material){
        Pallet pallet = null;
        EntityManager entityManager=null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pallet> criteria = criteriaBuilder.createQuery(Pallet.class);
            Root<Pallet> root = criteria.from(Pallet.class);
            criteria.where(criteriaBuilder.equal(root.get(Pallet_.material), material));
            TypedQuery<Pallet> typed = entityManager.createQuery(criteria);

            pallet = typed.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return pallet;
    }

    public void removeByID(Long id) {
        EntityManager entityManager=null;
        try
        {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            Pallet pallet = getPalletByID(id);
            entityManager.remove(pallet);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
