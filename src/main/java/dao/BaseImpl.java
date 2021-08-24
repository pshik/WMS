package dao;

import controllers.ControllerGUI;
import models.*;
import services.*;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseImpl implements Base{
    private static final String[] colNames = new String[]{"A","B","C","D","E","F","G","H","I"};
    private static final String[] rowNames = new String[]{"1","2","3","4","5","6","7","8","9"};
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
        if(users.isEmpty() || cells.isEmpty() || racks.isEmpty() || sapReferences.isEmpty()){
            initDefaultBase();
            reloadAll();
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

    public void initDefaultBase() {
        ServiceHibernate serviceHibernate = new ServiceHibernate();
        User user = new User("admin","Admin","GA",	"spb_admin03@grupoantolin.com",	"Administrator",	"12345");
        SapReference reference = new SapReference("Null",1,"ChangeName",new Long[]{});
        Rack rack = new Rack("ChangeName",2,2);
        serviceHibernate.save(user);
        serviceHibernate.save(reference);
        serviceHibernate.save(rack);
        for(int i = 0; i < rack.getRow(); i++){
            for( int j = 0; j < rack.getCol(); j++){
                Cell cell = new Cell(rack.getName() + ":" + colNames[j]+rowNames[i],i,j);
                serviceHibernate.save(cell);
            }
        }
    }
}
