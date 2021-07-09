package dao;

import controllers.ControllerGUI;
import models.*;
import services.*;
import utils.EntityManagerUtil;
import views.GUI;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BaseImpl implements Base{
    private List<Cell> cells = new ArrayList<>();
    private List<Pallet> pallets = new ArrayList<>();
    private List<Rack> racks = new ArrayList<>();
    private List<SapReference> sapReferences = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private final ServiceUser serviceUser = new ServiceUser();
    private final ServiceReference serviceReference = new ServiceReference();
    private final ServiceCell serviceCell = new ServiceCell();
    private final ServiceRack serviceRack = new ServiceRack();
    private final ServicePallet servicePallet = new ServicePallet();
    private ControllerGUI controllerGUI;

    public BaseImpl(ControllerGUI controller) {
        reloadAll();
        this.controllerGUI = controller;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Pallet> getPallets() {
        return pallets;
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

    public void reconnect(){

        if(EntityManagerUtil.getEntityManager() != null) EntityManagerUtil.shutdown();

    }
    @Override
    public void reloadAll() {
        try {
                users = serviceUser.getUsers();
                cells = serviceCell.getCells();
                pallets = servicePallet.getPallets();
                racks = serviceRack.getRacks();
                sapReferences = serviceReference.getReferences();
        } catch (Exception e){
            users = null;
            cells = null;
            pallets = null;
            racks = null;
            sapReferences = null;
            controllerGUI.logger.error("Base is not available!");
        }
    }

    @Override
    public void reloadTable(String tableName) {
        users.clear();
        cells.clear();
        pallets.clear();
        racks.clear();
        sapReferences.clear();
        reloadAll();
    }

    @Override
    public void save(String tableName, Object entity) {
        switch (tableName){
            case "Users":
                if(entity instanceof User) {
                    serviceUser.save((User) entity);
                } else {
                    System.out.println("Save: Entity don't instanceof User!");
                    controllerGUI.logger.warn("Save: Entity don't instanceof User!");
                }
                break;
            case "SapReferences":
                if(entity instanceof SapReference) {
                    serviceReference.save((SapReference) entity);
                } else {
                    System.out.println("Save: Entity don't instanceof SapReference!");
                    controllerGUI.logger.warn("Save: Entity don't instanceof SapReference!");
                }
                break;
            case "Racks":
                if(entity instanceof Rack) {
                    serviceRack.save((Rack) entity);
                } else {
                    System.out.println("Save: Entity don't instanceof Rack!");
                    controllerGUI.logger.warn("Save: Entity don't instanceof Rack!");
                }
                break;
            case "Pallets":
                if(entity instanceof Pallet) {
                    servicePallet.save((Pallet) entity);
                } else {
                    System.out.println("Save: Entity don't instanceof Pallet!");
                    controllerGUI.logger.warn("Save: Entity don't instanceof Pallet!");
                }
                break;
            case "Cells":
                if(entity instanceof Cell) {
                    serviceCell.save((Cell) entity);
                } else {
                    System.out.println("Save: Entity don't instanceof Cell!");
                    controllerGUI.logger.warn("Save: Entity don't instanceof Cell!");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void remove(String tableName, Long id) {
        switch (tableName){
            case "Users":
                serviceUser.removeByID(id);
                break;
            case "SapReferences":
                serviceReference.removeByID(id);
                break;
            case "Racks":
                serviceRack.removeByID(id);
                break;
            case "Pallets":
                servicePallet.removeByID(id);
                break;
            case "Cells":
                    serviceCell.removeByID(id);
                break;
            default:
                break;
        }
    }

    @Override
    public void close() {
        EntityManagerUtil.shutdown();
    }
}
