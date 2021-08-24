package controllers;

import dao.BaseImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import models.User;
import views.GUI;

import java.io.IOException;
import java.util.List;


public class ControllerGUI {
    private BaseImpl base;
    private GUI gui;
    private static BorderPane mainLayout;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuSettings;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private MenuItem menuConfiguration;

    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuTools;
    @FXML
    private Menu menuHelp;

    public void setGui(GUI gui){
        this.gui = gui;
    }
    public ControllerGUI() {
        this.base = new BaseImpl(this);
    }

    public BaseImpl getBase() {
        return base;
    }



    public void shutdown(){
        base.close();
    }
    @FXML
    private void closeApp() throws Exception {
        gui.closeApp();
    }

    @FXML
    private void configuration() throws IOException {
        GUI.showConfigView();
    }
}
