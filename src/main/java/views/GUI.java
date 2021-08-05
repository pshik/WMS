package views;

import controllers.ControllerGUI;
import controllers.ControllerLogin;
import controllers.ControllerRack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Rack;
import models.SapReference;
import models.User;

import java.io.IOException;
import java.util.List;

public class GUI extends Application {
    //public static PropertiesUtil propertiesUtil = new PropertiesUtil();
    private Stage primaryStage;
    public static ControllerGUI controller = new ControllerGUI();
    private static BorderPane mainLayout;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("GA WMS");

        showMainWindow();
        showLoginView();
    }

    private  void showMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getProtectionDomain().getClassLoader().getResource("fxml/MainApp.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showRackView(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getProtectionDomain().getClassLoader().getResource("fxml/RackView.fxml"));
        BorderPane rackPane = loader.load();
        ControllerRack controllerRack = loader.getController();
        controllerRack.currentUser.setText(s);
        List<SapReference> sapReferences = controller.getBase().getSapReferences();
        sapReferences.forEach(sapReference -> controllerRack.cmbReference.getItems().add(sapReference.getName()));
        List<Rack> racks = controller.getBase().getRacks();
        racks.forEach(rack -> controllerRack.cmbRack.getItems().add(rack.getName()));
        controllerRack.cmbRack.setValue(racks.get(0).getName());
        controllerRack.showRack(controllerRack.cmbRack.getValue());

        mainLayout.setCenter(rackPane);
    }
    private  void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((GUI.class.getProtectionDomain().getClassLoader().getResource("fxml/LoginView.fxml")));
        BorderPane loginPane = loader.load();
        ControllerLogin controllerLogin = loader.getController();
        List<User> users = controller.getBase().getUsers();
        users.forEach(user -> controllerLogin.cmbUsers.getItems().add(user.getUsername()));
        controllerLogin.cmbUsers.setValue(users.get(0).getUsername());
        mainLayout.setCenter(loginPane);


    }
    @Override
    public void stop() throws Exception {
        controller.shutdown();
        super.stop();
    }
}
