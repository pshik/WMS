package controllers;

import dao.BaseImpl;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ControllerGUI {
    private BaseImpl base;
    public Logger logger = LoggerFactory.getLogger(ControllerGUI.class);


    public ControllerGUI() {
        String path = ControllerGUI.class.getProtectionDomain().getClassLoader().getResource("logs/log4j.properties").getPath();
        PropertyConfigurator.configure(path);
        logger.warn("GUI is started!");
        this.base = new BaseImpl(this);

    }

    public BaseImpl getBase() {
        return base;
    }



    public void shutdown(){
        base.close();
    }
}
