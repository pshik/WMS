package services;

import metamodels.Cell_;
import models.Cell;
import models.User;
import utils.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServiceCell {
    public ServiceCell() {
    }
    public Cell getCellByID(long id){
        Cell cell = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            cell = entityManager.find(Cell.class, id);
            entityManager.detach(cell);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return cell;
    }

    @SuppressWarnings("unchecked")
    public List<Cell> getCells(){
        List<Cell> list = null;
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            list = entityManager.createQuery("from Cell").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return list;
    }

    public void save (Cell reference){

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

    public void removeCellByID(long id){

    }
    public Cell getCellByName(String name){
        Cell cell = null;
        EntityManager entityManager=null;
        try{
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cell> criteria = criteriaBuilder.createQuery(Cell.class);
            Root<Cell> root = criteria.from(Cell.class);
            criteria.where(criteriaBuilder.equal(root.get(Cell_.name), name));
            TypedQuery<Cell> typed = entityManager.createQuery(criteria);

            cell = typed.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
        return cell;
    }

    public void removeByID(Long id) {
        EntityManager entityManager=null;
        try
        {
            entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            Cell cell = getCellByID(id);
            entityManager.remove(cell);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager != null) {
                entityManager.getTransaction().rollback();
            }
        }
    }
}
