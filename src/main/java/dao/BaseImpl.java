package dao;

import controllers.ControllerGUI;
import models.*;
import services.*;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseImpl implements Base{
    private List<Cell> cells = new ArrayList<>();
    private List<Rack> racks = new ArrayList<>();
    private List<SapReference> sapReferences = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private final ServiceHibernate serviceHibernate = new ServiceHibernate();
    private ControllerGUI controllerGUI;

    public BaseImpl(ControllerGUI controller) {
        reloadAll();
        this.controllerGUI = controller;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public List<SapReference> getSapReferences() {
        return sapReferences;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void reloadAll() {
        try {
            users = serviceHibernate.getAllData(User.class);
            cells = serviceHibernate.getAllData(Cell.class);
            racks = serviceHibernate.getAllData(Rack.class);
            sapReferences = serviceHibernate.getAllData(SapReference.class);
        } catch (Exception e){
            users = null;
            cells = null;
            racks = null;
            sapReferences = null;
            ServiceLogger.writeErrorLog(this.getClass(),"Base is not available!");
        }
    }

    @Override
    public void reloadTable(Class theClass) {
        if (User.class.equals(theClass)) {
            users = null;
            users = serviceHibernate.getAllData(theClass);
        } else if (Rack.class.equals(theClass)) {
            racks = null;
            racks = serviceHibernate.getAllData(theClass);
        } else if (Cell.class.equals(theClass)) {
            cells = null;
            cells = serviceHibernate.getAllData(theClass);
        } else if (SapReference.class.equals(theClass)) {
            sapReferences = null;
            sapReferences = serviceHibernate.getAllData(theClass);
        }
    }

    @Override
    public void save(Object entity) {
        serviceHibernate.save(entity);
    }

    @Override
    public void update( Object entity) {
        serviceHibernate.update(entity);
    }

    @Override
    public void removeById(Class theClass, Long id) {
        serviceHibernate.removeByID(theClass,id);
    }


    @Override
    public Object getObjectById(Class theClass, long id){
        return serviceHibernate.getObjectById(theClass,id);
    }
    @Override
    public Object getObjectByField(Class theClass,String value,String field){
        return serviceHibernate.getObjectByField(theClass,value,field);
    }

    @Override
    public void close() {
        HibernateUtil.closeSessionFactory();
    }
}
