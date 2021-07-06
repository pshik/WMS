package controllers;

import dao.BaseImpl;

public class ControllerGUI {
    private BaseImpl base = new BaseImpl();
    public ControllerGUI() {

    }

    public BaseImpl getBase() {
        return base;
    }

    public void shutdown(){
        base.close();
    }
}
