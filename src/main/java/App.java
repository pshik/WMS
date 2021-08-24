import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class App extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("GA WMS");

        StackPane root = new StackPane();
        stage.setScene(new Scene(root, 300, 250));
        ObservableList<Os> items = FXCollections.observableArrayList();
        Os os = new Os("Test1",true);
        Os os1 = new Os("Test2",false);
        Os os2 = new Os("Test3",false);
        items.addAll(os,os1,os2);

        TableView<Os> tableView = new TableView();
        ObservableList<TableColumn<Os, ?>> columns = tableView.getColumns();

        TableColumn<Os, String> nameColumn = new TableColumn<>( "Name" );
        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ));
        columns.add(  nameColumn );

        final TableColumn<Os, Boolean> loadedColumn = new TableColumn<>( "isAvailable" );
        loadedColumn.setCellValueFactory( new PropertyValueFactory<>( "isAvailable" ));
        loadedColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
        columns.add( loadedColumn );
        final Button delBtn = new Button( "Delete" );
        delBtn.setMaxWidth( Double.MAX_VALUE );
        delBtn.setOnAction( e -> {
            final Set<Os> del = new HashSet<>();
            for( final Os o : tableView.getItems()) {
                if( o.isAvailableProperty().get()) {
                    del.add( o );
                }
            }
            tableView.getItems().removeAll( del );
        });
        tableView.setItems(items);
        tableView.setEditable(true);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TableColumn<Os, ?> osTableColumn = tableView.getColumns().get(1);
                Object cellData = osTableColumn.getCellData(0);
                Object cellData1 = osTableColumn.getCellData(1);
                Object cellData2 = osTableColumn.getCellData(2);
                System.err.println("0" + cellData);
                System.out.println("1" + cellData1);
                System.err.println("2" + cellData2);
            }
        });
        root.getChildren().addAll(tableView,button);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public class Os {

        private final StringProperty name   = new SimpleStringProperty();
        private final BooleanProperty isAvailable = new SimpleBooleanProperty();

        public Os( String nm, boolean del ) {
            name  .set( nm  );
            isAvailable.set( del );
        }

        public StringProperty  nameProperty  () { return name;   }
        public BooleanProperty isAvailableProperty() { return isAvailable; }
    }
}

