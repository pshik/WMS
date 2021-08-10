package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.Cell;
import models.Pallet;
import models.Rack;
import models.SapReference;
import services.ServiceLogger;
import utils.PropertiesUtil;
import utils.ViewCellUtil;
import views.GUI;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerRack {
    private GUI gui;
    @FXML
    private ComboBox<String> cmbReference;
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnPickUp;
    @FXML
    private Pane rackPane;
    @FXML
    private ComboBox<String> cmbRack;
    @FXML
    private Label cellInfoAddress;
    @FXML
    private Label cellInfoReference;
    @FXML
    private Label cellInfoDate;
    @FXML
    private Label currentUser;
    @FXML
    private RadioButton showAvailableCells;
    @FXML
    private RadioButton showMaterials;
    @FXML
    private CheckBox manualDate;
    @FXML
    private CheckBox forcePickUp;


    public ComboBox<String> getCmbReference() {
        return cmbReference;
    }

    public ComboBox<String> getCmbRack() {
        return cmbRack;
    }

    public Label getCurrentUser() {
        return currentUser;
    }

    public Label getCellInfoDate() {
        return cellInfoDate;
    }

    private static Map<String,List<Integer>> map = new HashMap<>();
    private List<Cell> cells;
    private List<Rack> racks;
    private List<SapReference> references;
    private ViewCellUtil viewCellUtil = new ViewCellUtil();
    private List<Button> rackList = new ArrayList<>();


    static {
        map.put("4",Arrays.asList(3));
        map.put("5",Arrays.asList(3));
        map.put("6",Arrays.asList(3));
        map.put("2",Arrays.asList(2,3));
        map.put("3",Arrays.asList(2,3));
        map.put("1",Arrays.asList(1,2,3));


    }

    public ControllerRack() {

    }

    @FXML
    private void loadReference(){
        if(showAvailableCells.isSelected()){
            Button btn = rackList.stream().filter(button -> button.getId().equals(cellInfoAddress.getText())).findAny().orElse(null);
            markCellsAsBusy();
            if(btn != null) {
                if (viewCellUtil.checkAvailableButton(btn)) {
                    cellInfoReference.setText(cmbReference.getValue());
                    Cell cell = cells.stream().filter(c -> c.getAddress().equals(cmbRack.getValue() + ":" + cellInfoAddress.getText().split(":")[0])).findAny().orElse(null);
                    int i = Integer.parseInt(cellInfoAddress.getText().split(":")[1]);
                    SapReference reference = references.stream().filter(r -> r.getName().equals(cmbReference.getValue())).findAny().orElse(null);

                    Pallet pallet = new Pallet();
                    pallet.setMaterial(cmbReference.getValue());
                    pallet.setPosition(i);
                    pallet.setSize(reference.getSize());
                    pallet.setLoadingDate(new Date());

                    if (cell.addPallet(pallet)) {
                        GUI.controller.getBase().update(cell);
                        GUI.controller.getBase().reloadTable(Cell.class);
                    }
                   // showMaterials.setDisable(false);
                    showAvailableCells.setSelected(false);
                    changeAvailableInterfaceStatus(false);
                    showRack(cmbRack.getValue(), cmbReference.getValue());
                }
            }
        }
    }

    private void markCellsAsBusy() {
        List<Cell> cellsOfRack = this.cells.stream().filter(c -> c.getAddress().split(":")[0].equals(cmbRack.getValue())).toList();
        for (Cell cell: cellsOfRack) {
            List<Button> buttons = rackList.stream().filter(button -> button.getId().split(":")[0].equals(cell.getAddress().split(":")[1])).toList();

            for (Button btn: buttons){
                if(btn.getText().equals("")) {
                    boolean free = true;
                    switch (btn.getId().split(":")[1]) {
                        case "1":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    if (!b.getText().equals("")) {
                                        free=false;
                                    }
                                }
                            }
                            break;
                        case "2":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    int pos = Integer.parseInt(b.getId().split(":")[1]);
                                    if(pos == 1 || pos == 4 || pos == 5) {
                                        if (!b.getText().equals("")) {
                                            free = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "3":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    int pos = Integer.parseInt(b.getId().split(":")[1]);
                                    if(pos == 1 || pos == 5 || pos == 6) {
                                        if (!b.getText().equals("")) {
                                            free = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "4":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    int pos = Integer.parseInt(b.getId().split(":")[1]);
                                    if(pos == 1 || pos == 2) {
                                        if (!b.getText().equals("")) {
                                            free = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "5":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    int pos = Integer.parseInt(b.getId().split(":")[1]);
                                    if(pos == 1 || pos == 2 || pos == 3) {
                                        if (!b.getText().equals("")) {
                                            free = false;
                                        }
                                    }
                                }
                            }
                            break;
                        case "6":
                            for (Button b : buttons) {
                                if (b != btn) {
                                    int pos = Integer.parseInt(b.getId().split(":")[1]);
                                    if(pos == 1 || pos == 3) {
                                        if (!b.getText().equals("")) {
                                            free = false;
                                        }
                                    }
                                }
                            }
                            break;
                    }
                    if (free) {
                        viewCellUtil.setDefault(btn);
                    } else {
                        viewCellUtil.setUnAvailable(btn);
                    }
                }else {
                    viewCellUtil.setBusyStatus(btn);
                }
            }

        }
    }
    public void init(){
        cmbRack.setEditable(false);
        cmbReference.setEditable(false);
        showAvailableCells.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(showMaterials.isSelected()) showMaterials.setSelected(false);
                if(showAvailableCells.isSelected()){
                    changeAvailableInterfaceStatus(true);
                    showAvailableCells();
                } else {
                    changeAvailableInterfaceStatus(false);
                    btnPickUp.setDisable(false);
                    markCellsAsBusy();
                }
            }
        });
        showMaterials.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(showAvailableCells.isSelected()) showAvailableCells.setSelected(false);
                if(showMaterials.isSelected()){
                    cmbRack.setDisable(true);
                    cmbReference.setDisable(true);
                    manualDate.setDisable(true);
                    forcePickUp.setDisable(true);
                    showAvailableCells.setDisable(true);
                    btnLoad.setDisable(true);
                    btnPickUp.setDisable(true);
                    showAllRefOnRack();
                } else {
                    cmbRack.setDisable(false);
                    cmbReference.setDisable(false);
                    manualDate.setDisable(false);
                    forcePickUp.setDisable(false);
                    showAvailableCells.setDisable(false);
                    btnLoad.setDisable(false);
                    btnPickUp.setDisable(false);
                    markCellsAsBusy();
                }
            }
        });
    }

    private void changeAvailableInterfaceStatus(boolean b) {
        cmbRack.setDisable(b);
        cmbReference.setDisable(b);
        manualDate.setDisable(b);
        forcePickUp.setDisable(b);
        showMaterials.setDisable(b);
        btnPickUp.setDisable(b);
    }

    @FXML
    private void pickUpReference(){
        showMaterialForPickUP( cmbReference.getValue(), forcePickUp.isSelected());
    }
    @FXML
    private void changeRack(){
        rackPane.getChildren().clear();
        showRack(cmbRack.getValue(),null);
    }

    @FXML
    private void showAvailableCells(){
        for (Button btn: rackList
        ) {
            String name = btn.getId();
            if(btn.getText().equals("")) {
                List<Integer> list = map.get(name.split(":")[1]);
                SapReference reference = references.stream().filter(ref -> ref.getName().equals(cmbReference.getValue())).findAny().orElse(null);
                if (list.contains(reference.getSize()) && viewCellUtil.checkAvailableButton(btn)) {
                    viewCellUtil.setAvailable(btn);
                } else {
                    viewCellUtil.setUnAvailable(btn);
                }
            } else {
                viewCellUtil.setBusyStatus(btn);
            }
        }
    }

    private void showAllRefOnRack(){
        markCellsAsBusy();
        for(Button b: rackList){
            if(!btnPickUp.getText().equals("")){
                if(b.getText().equals(cmbReference.getValue())){
                    viewCellUtil.highlightRef(b);
                }
            }
        }

    }

    @FXML
    public void showRack(String rackName, String ref){

        updateLists();
        rackList.clear();
        if(ref == null) {
            cmbReference.setValue(cmbReference.getItems().get(0));
        } else {
            cmbReference.setValue(ref);
        }
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
                Pallet[] pallets = cell.getPallets();

                VBox cellVbox = new VBox();
                double rowChildrenHeight = rowHeight-5;
                setSize(cellVbox,colWidth,rowChildrenHeight);

                cellVbox.setId(cellName);
                cellVbox.setSpacing(2);
                Button position0 = new Button();
                btnConfig(position0, colWidth,(rowChildrenHeight-4)/3,cellName,1,pallets);

                Button position1 = new Button();
                btnConfig(position1, (colWidth-2)/2,(rowChildrenHeight-4)/3,cellName,2,pallets);

                Button position2 = new Button();
                btnConfig(position2, (colWidth-2)/2,(rowChildrenHeight-4)/3,cellName,3,pallets);

                Button position3 = new Button();
                btnConfig(position3, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,4,pallets);

                Button position4 = new Button();
                btnConfig(position4, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,5,pallets);

                Button position5 = new Button();
                btnConfig(position5, (colWidth-4)/3,(rowChildrenHeight-4)/3,cellName,6,pallets);

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
                rackList.addAll(Arrays.asList(position0, position1, position2, position3, position4, position5));
            }
            table.getChildren().add(rowHbox);
        }
        rackPane.getChildren().add(table);
        markCellsAsBusy();
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
        GUI.controller.getBase().reloadAll();
        racks = null;
        cells = null;
        references = null;
        racks = GUI.controller.getBase().getRacks();
        cells = GUI.controller.getBase().getCells();
        references = GUI.controller.getBase().getSapReferences();
    }
    public void startTimer(){
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(Integer.parseInt(PropertiesUtil.getProperties().getProperty("timer.for.refresh.sec","5"))),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("this is called every 10 seconds on UI thread");
                                if(!showAvailableCells.isSelected() && !showMaterials.isSelected() && !manualDate.isSelected() && !forcePickUp.isSelected()) {
                                    showRack(cmbRack.getValue(),cmbReference.getValue());
                                }
                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
    private void btnConfig(Button button, double btnWidth, double btnHeight, String cellName, int position,Pallet[] pallets) {
        viewCellUtil.setDefault(button);
        setSize(button,btnWidth,btnHeight);
        button.setId(cellName + ":"+position);
        Pallet currPallet = null;
       // DataBuilder data = new DataBuilder();
        for (Pallet p : pallets) {
            if(p != null ) {
                if (p.getPosition() == position) {
                    button.setText(p.getMaterial());
                    currPallet = p;
                    viewCellUtil.setBusyStatus(button);
                } else {
                    button.setText("");
                }
            }
        }

        Pallet finalPallet = currPallet;
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clearCellInfo();
                cellInfoAddress.setText(cellName + ":" + position);
                cellInfoReference.setText(button.getText());
                if(finalPallet !=null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellInfoDate.setText(format.format(finalPallet.getLoadingDate()));
                }
                if(!showAvailableCells.isSelected()){
                    markCellsAsBusy();
                }
                viewCellUtil.selectedCell(button);
            }
        });
    }

    private void showMaterialForPickUP(String material, boolean isForced){
        if(isForced){
            Button btn = rackList.stream().filter(button -> button.getId().equals(cellInfoAddress.getText())).findAny().orElse(null);
            markCellsAsBusy();
            if(btn != null) {
                int position = Integer.parseInt(cellInfoAddress.getText().split(":")[1]);
                Cell cell = cells.stream().filter(c -> c.getAddress().equals(cmbRack.getValue()+":"+cellInfoAddress.getText().split(":")[0])).findAny().orElse(null);
                Pallet[] pallets = cell.getPallets();
                for( int i = 0; i<pallets.length; i++){
                    if(pallets[i] != null) {
                        if (pallets[i].getPosition() == position) {
                            cell.deletePallet(pallets[i].getPosition());
                        }
                    }
                }
                forcePickUp.setSelected(false);
                GUI.controller.getBase().update(cell);
                showRack(cmbRack.getValue(),cmbReference.getValue());
            }
        } else {
            LocalDateTime currentDate = LocalDateTime.now();
            currentDate = currentDate.minusDays(Integer.parseInt(PropertiesUtil.getProperties().getProperty("days.lock","4")));
            ArrayList<Cell> selectedCells = new ArrayList<>();
            for(Cell cell: cells){
                for (Pallet p: cell.getPallets()) {
                    if(p != null) {
                        if (p.getMaterial().equals(cmbReference.getValue())) {
                            selectedCells.add(cell);
                            break;
                        }
                    }
                }
            }
            Pallet pallet = null;
            Cell cell = null;
            if(selectedCells.size() >0) {
                for (Cell c : selectedCells) {
                    for (Pallet p : c.getPallets()) {
                        if(p != null) {
                            LocalDateTime tmp = convertToLocalDateTime(p.getLoadingDate());
                            if (tmp.isBefore(currentDate)) {
                                pallet = p;
                                cell = c;
                                currentDate = convertToLocalDateTime(p.getLoadingDate());
                            }
                        }
                    }
                }
                if (cell != null) {
                    String rackName = cell.getAddress().split(":")[0];
                    if (!rackName.equals(cmbRack.getValue())) {
                        rackPane.getChildren().clear();
                        cmbRack.setValue(rackName);
                        showRack(rackName, cmbReference.getValue());
                    }

                    Cell finalCell = cell;
                    Pallet finalPallet = pallet;
                    Button btn = rackList.stream().filter(button -> button.getId().equals(finalCell.getAddress().split(":")[1] + ":" + finalPallet.getPosition())).findAny().orElse(null);
                    viewCellUtil.highlightFIFO(btn);
                }

                if(selectedCells.size()<3){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "На стеллажах останется последний паллет с материалом " + material, ButtonType.OK);
                    ServiceLogger.writeInfoLog(this.getClass(),String.format("Пользователь %s, предупрежден о том что остается последний паллет c материалом %s на стеллажах", currentUser.getText(), material));
                    alert.showAndWait();
                }
                ButtonType yes = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert question = new Alert(Alert.AlertType.CONFIRMATION
                        ,"Снять паллет с материалом " + pallet.getMaterial() + " из ячейки " + cell.getAddress().split(":")[1] + " или отменить действие?"
                        ,yes
                        ,cancel);
                question.setTitle("Снятие паллета");
                Optional<ButtonType> result = question.showAndWait();
                if (result.orElse(cancel) == yes) {
                    cell.deletePallet(pallet.getPosition());
                    GUI.controller.getBase().update(cell);
                    showRack(cmbRack.getValue(),cmbReference.getValue());
                }
            }
        }
    }

    private void clearCellInfo() {
        cellInfoAddress.setText("");
        cellInfoDate.setText("");
        cellInfoReference.setText("");
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public Date convertToDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
