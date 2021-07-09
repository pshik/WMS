package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Cell;
import models.Pallet;
import models.Rack;
import models.SapReference;
import utils.ViewCellUtil;
import views.GUI;


import java.util.*;
import java.util.stream.Collectors;

public class ControllerRack {
    private GUI gui;
    @FXML
    public ComboBox<String> cmbReference;
    @FXML
    public Button btnLoad;
    @FXML
    public Button btnPickUp;
    @FXML
    public TextArea txtLog;
    @FXML
    public Pane rackPane;
    @FXML
    public ComboBox<String> cmbRack;

    @FXML
    public Label cellInfoAddress;
    @FXML
    public Label cellInfoReference;
    @FXML
    public Label cellInfoDate;
    @FXML
    public Label currentUser;
    private static Map<String,List<Integer>> map = new HashMap<>();
    private List<Pallet> pallets;
    private List<Cell> cells;
    private List<Rack> racks;
    private List<SapReference> references;
    private ViewCellUtil viewCellUtil = new ViewCellUtil();

   // private Image    iconPallet = new Image(GUI.class.getProtectionDomain().getClassLoader().getResource("/icons/pallet.png").toString());
   // private Image iconEmpty = new Image(GUI.class.getProtectionDomain().getClassLoader().getResource("/icons/empty.png").toString());
    static {
        map.put("4",Arrays.asList(3));
        map.put("5",Arrays.asList(3));
        map.put("6",Arrays.asList(3));
        map.put("2",Arrays.asList(2,3));
        map.put("3",Arrays.asList(2,3));
        map.put("1",Arrays.asList(1,2,3));
    }


    @FXML
    private void loadReference(){

    }

    @FXML
    private void pickUpReference(){

    }
    @FXML
    private void changeRack(){
        rackPane.getChildren().clear();
        loadRack(cmbRack.getValue());
        //showAvailableCells();
    }

    @FXML
    private void showAvailableCells(){
        for (Node r : rackPane.getChildren()) {
            VBox row = (VBox) r;
            for (Node c: row.getChildren()) {
                HBox pall = (HBox) c;
                for(Node p: pall.getChildren()) {
                    if(p instanceof VBox) {
                        VBox rowInCell = (VBox) p;
                        for (Node pal : rowInCell.getChildren()) {
                            if (pal instanceof Button) {
                                viewCellUtil.setDefault((Button) pal);
                                btnStatus((Button) pal);
                            } else if (pal instanceof HBox) {
                                HBox hBox = (HBox) pal;
                                for (Node b : hBox.getChildren()) {
                                    viewCellUtil.setDefault((Button) b);
                                    btnStatus((Button) b);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void btnStatus(Button btn) {
        String name = btn.getId();
        if(btn.getText().equals("")) {
            List<Integer> list = map.get(name.split(":")[1]);
            SapReference reference = references.stream().filter(ref -> ref.getName().equals(cmbReference.getValue())).findAny().orElse(null);
            if (list.contains(reference.getSize())) {
                viewCellUtil.setDefault(btn);
            } else {
                viewCellUtil.setUnAvailable(btn);
            }
        }
    }

    @FXML
    public void loadRack(String rackName){

        updateLists();
        Rack rack = racks.stream().filter(r -> rackName.equals(r.getName())).findAny().orElse(null);

        clearCellInfo();


        int currentRackRowCount = rack.getRow();
        int currentRackColumnCount = rack.getCol();

        List<Cell> cellsOfRack = cells.stream().filter(c -> rackName.equals(c.getAddress().split(":")[0])).collect(Collectors.toList());


        double width = 980;
        double height = 710;
        setSize(rackPane,width,height);

        double headerHeight = 30;
        double numberRowWidth = 40;
        double rowHeight = (height - headerHeight - 5)/rack.getRow();
        double colWidth = (width - numberRowWidth - 5*currentRackColumnCount)/rack.getCol();

        VBox table = new VBox();
        setSize(table,width,height);
        table.setSpacing(5);

        HBox colHeaderHbox = new HBox();
        setSize(colHeaderHbox,width,headerHeight);
        colHeaderHbox.setSpacing(5);
        Button numberRow = new Button("#");
        setSize(numberRow,numberRowWidth,headerHeight);

        numberRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearCellInfo();
            }
        });

        colHeaderHbox.getChildren().add(numberRow);

        for(int j =0;j < currentRackColumnCount; j++){
            Character name = (char) (65+j);
            Button colHeader = new Button(name.toString());
            colHeader.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    clearCellInfo();
                }
            });
            setSize(colHeader,colWidth,headerHeight);
            colHeaderHbox.getChildren().add(colHeader);
        }
        table.getChildren().add(colHeaderHbox);

        for(int i = currentRackRowCount; i > 0; i--){
            HBox rowHbox = new HBox();
            setSize(rowHbox,width,rowHeight);
            rowHbox.setSpacing(5);
            Button row = new Button(""+i);
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    clearCellInfo();
                }
            });
            setSize(row,numberRowWidth,rowHeight);

            rowHbox.getChildren().add(row);

            for(int j =0;j < currentRackColumnCount; j++){
                Character name = (char) (65+j);
                String cellName = name.toString()+(i);
                Cell cell = cellsOfRack.stream().filter(c -> cellName.equals(c.getAddress().split(":")[1])).findAny().orElse(null);
                Long[] palletIDs = cell.getPalletIDs();

                VBox cellVbox = new VBox();
                double rowChildrenHeight = rowHeight-5;
                setSize(cellVbox,colWidth,rowChildrenHeight);

                cellVbox.setId(cellName);
                cellVbox.setSpacing(2);
                Button position0 = new Button();
                btnConfig(position0, colWidth,(rowChildrenHeight-4)/3,cellName,"1");

                Button position1 = new Button();
                btnConfig(position1, (colWidth-2)/2,(rowChildrenHeight-4)/3,cellName,"2");

                Button position2 = new Button();
                btnConfig(position2, (colWidth-2)/2,(rowChildrenHeight-4)/3,cellName,"3");

                Button position3 = new Button();
                btnConfig(position3, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,"4");

                Button position4 = new Button();
                btnConfig(position4, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,"5");

                Button position5 = new Button();
                btnConfig(position5, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,"6");

                cellVbox.getChildren().add(position0);
                HBox line1 = new HBox();
                setSize(line1,colWidth,rowChildrenHeight/3);
                line1.getChildren().add(position1);
                line1.getChildren().add(position2);
                line1.setSpacing(2);
                cellVbox.getChildren().add(line1);
                HBox line2 = new HBox();
                setSize(line2,colWidth,rowChildrenHeight/3);
                line2.getChildren().add(position3);
                line2.getChildren().add(position4);
                line2.getChildren().add(position5);
                line2.setSpacing(2);
                cellVbox.getChildren().add(line2);
                rowHbox.getChildren().add(cellVbox);
            }
            table.getChildren().add(rowHbox);
        }
        rackPane.getChildren().add(table);
    }

    private void setSize(Node node, double width, double height) {
        if(node instanceof Button) {
            Button button = (Button) node;
            button.setMinSize(width, height);
            button.setPrefSize(width, height);
            button.setMaxSize(width, height);
        }else if(node instanceof VBox){
            VBox vBox = (VBox) node;
            vBox.setMinSize(width, height);
            vBox.setPrefSize(width, height);
            vBox.setMaxSize(width, height);
        }else if(node instanceof HBox){
            HBox hBox = (HBox) node;
            hBox.setMinSize(width, height);
            hBox.setPrefSize(width, height);
            hBox.setMaxSize(width, height);
        }else if(node instanceof Pane){
            Pane pane = (Pane) node;
            pane.setMinSize(width, height);
            pane.setPrefSize(width, height);
            pane.setMaxSize(width, height);
        }
    }

    private void updateLists() {
        racks = GUI.controller.getBase().getRacks();
        cells = GUI.controller.getBase().getCells();
        pallets = GUI.controller.getBase().getPallets();
        references = GUI.controller.getBase().getSapReferences();
    }

    private void btnConfig(Button button, double btnWidth, double btnHeight, String cellName, String position) {
        viewCellUtil.setDefault(button);
        button.setMinSize(btnWidth,btnHeight);
        button.setPrefSize(btnWidth,btnHeight);
        button.setId(cellName + ":"+position);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearCellInfo();
                cellInfoAddress.setText(cellName + " [" + position+ "]");

            }
        });
    }
    private void clearCellInfo() {
        cellInfoAddress.setText("");
        cellInfoDate.setText("");
        cellInfoReference.setText("");
    }
}
