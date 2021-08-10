package controllers;

import dao.BaseImpl;


public class ControllerGUI {
    private BaseImpl base;

    public ControllerGUI() {
        this.base = new BaseImpl(this);

    }

    public BaseImpl getBase() {
        return base;
    }



    public void shutdown(){
        base.close();
    }
}
