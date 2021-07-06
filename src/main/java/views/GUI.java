package views;

import controllers.ControllerGUI;
import dao.Base;
import dao.BaseImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertiesUtil;

public class GUI extends Application {
    public static PropertiesUtil propertiesUtil = new PropertiesUtil();
    public static Logger logger = LoggerFactory.getLogger(GUI.class);
    public static ControllerGUI controller = new ControllerGUI();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        String path = GUI.class.getProtectionDomain().getClassLoader().getResource("logs/log4j.properties").getPath();
        PropertyConfigurator.configure(path);
        logger.warn("GUI is started!");

        controller.getBase().getUsers().forEach(user -> System.out.println(user.toString()));

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainApp.fxml"));
        stage.setTitle("GA WMS");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        controller.shutdown();
    }
}
