package utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlertDialog {

    static LocalDate date;
    static LocalDateTime dateTime = LocalDateTime.now();

    public static LocalDateTime getDate(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        DateTimePicker dateTimePicker = new DateTimePicker();

        dateTimePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dateTime = dateTimePicker.getDateTimeValue();

            }
        });

        Button button = new Button("Close");
        button.setOnAction(e -> {
            stage.close();
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(2);
        layout.setHgap(3);
        layout.add(button, 2,3);
        layout.add(dateTimePicker,1,1);

        Scene scene = new Scene(layout, 250, 100);
        stage.setTitle("DatePicker");
        stage.setScene(scene);
        stage.showAndWait();

        return dateTime;
    }
}