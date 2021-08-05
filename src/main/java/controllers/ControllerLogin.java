package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.User;
import views.GUI;
import java.io.IOException;


public class ControllerLogin {
    private GUI gui;
    @FXML
    public ComboBox<String> cmbUsers ;
    @FXML
    public PasswordField pwdField;
    @FXML
    public Button btnLogin;

    @FXML
    public void goRackView(String s) throws IOException {
        gui.showRackView(s);
    }

    public ControllerLogin() {
    }
    @FXML
    void keyEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    private void login() throws IOException {

        for (User u:
                GUI.controller.getBase().getUsers()) {
            if(u.getUsername().equals(cmbUsers.getValue())){
                if(u.getPassword().equals(pwdField.getText())){
                    goRackView(u.getFirstName() + " " + u.getLastName());
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wrong password! Please try again", ButtonType.OK);
                    alert.showAndWait();
                }
                break;
            }
        }

    }

}
