package controllers;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import models.*;
import models.Cell;

import views.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;


import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;


public class ControllerConfig {
    private List<Rack> rackList = new ArrayList<>();
    private List<SapReference> referenceList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Cell> cellList = new ArrayList<>();


    private GUI view;
    @FXML
    private TabPane tabMain;
    @FXML
    private Tab tabImport,tabExport,tabReference,tabRack,tabUser;
    @FXML
    private Button btnChooseFile,btnImport,btnChooseFolder,btnExport,btnRefUpdate,btnRefNew,btnRefDelete;
    @FXML
    private Button btnRackUpdate,btnRackNew,btnRackDelete,btnUserUpdate,btnUserNew,btnUserDelete;
    @FXML
    private ComboBox cmbReference,cmbUser,cmbRack,cmbRole,cmbSize;
    @FXML
    private RadioButton rbReferences,rbRacks,rbUsers,rbCells;
    @FXML
    private RadioButton rbReferencesEx,rbRacksEx,rbUsersEx,rbCellsEx;
    @FXML
    private ScrollPane loadingPane,unLoadingPane;
    @FXML
    private ToggleGroup loadingTypeIm,loadingTypeEx;
    @FXML
    private TextField txtRackRow,txtRackColumn,txtFirstName,txtLastName,txtUsername,txtEmail;
    @FXML
    private TextArea txtDescription;
    @FXML
    private PasswordField pswdPasword;
    @FXML
    private TableView<RowItem>  tblRefRackList,tblRackRefList,tblCells;
    @FXML
    private TableView tableData;




    public ControllerConfig() {
    }

    @FXML
    private void closeConfig() throws IOException {
        GUI.showRackView(GUI.controller.getBase().getRacks().get(0).getName());
    }

    public void init(){

        referenceList = GUI.controller.getBase().getSapReferences();
        rackList = GUI.controller.getBase().getRacks();
        userList = GUI.controller.getBase().getUsers();
        cellList = GUI.controller.getBase().getCells();
        tabMain.getSelectionModel().select(tabReference);
        cmbSize.getItems().add(" ");
        cmbSize.getItems().add(1);
        cmbSize.getItems().add(2);
        cmbSize.getItems().add(3);
        cmbRole.getItems().add(" "); //index 0
        cmbRole.getItems().add(UserRoles.ADMIN);  //index 1
        cmbRole.getItems().add(UserRoles.MANAGER); //index 2
        cmbRole.getItems().add(UserRoles.KEEPER); //index 3
        cmbRole.getItems().add(UserRoles.DRIVER); //index 4

        ObservableList columnsRef = tblRefRackList.getColumns();
        ObservableList columnsCell = tblCells.getColumns();
        ObservableList columnsRack = tblRackRefList.getColumns();

        TableColumn<RowItem, String> nameRack = new TableColumn<>( "Rack" );
        nameRack.setCellValueFactory( new PropertyValueFactory<>( "name" ));
        columnsRef.add(nameRack);

        TableColumn<RowItem, String> nameRef = new TableColumn<>( "Reference" );
        nameRef.setCellValueFactory( new PropertyValueFactory<>( "name" ));
        columnsRack.add(nameRef);

        TableColumn<RowItem, String> nameCell = new TableColumn<>( "Cell" );
        nameCell.setCellValueFactory( new PropertyValueFactory<>( "name" ));
        columnsCell.add(nameCell);

        final TableColumn<RowItem, Boolean> statusRef = new TableColumn<>( "isAvailable?" );
        statusRef.setCellValueFactory( new PropertyValueFactory<>( "status" ));
        statusRef.setCellFactory( tc -> new CheckBoxTableCell<>());
        final TableColumn<RowItem, Boolean> statusRack = new TableColumn<>( "isAllowed?" );
        statusRack.setCellValueFactory( new PropertyValueFactory<>( "status" ));
        statusRack.setCellFactory( tc -> new CheckBoxTableCell<>());
        final TableColumn<RowItem, Boolean> statusCell = new TableColumn<>( "isBlocked?" );
        statusCell.setCellValueFactory( new PropertyValueFactory<>( "status" ));
        statusCell.setCellFactory( tc -> new CheckBoxTableCell<>());

        columnsRef.add( statusRef );
        columnsCell.add( statusCell );
        columnsRack.add( statusRack );

        final Button statusBtn = new Button( );
        statusBtn.setMaxWidth( Double.MAX_VALUE );
        statusBtn.setOnAction( e -> {
            final Set<RowItem> st = new HashSet<>();
            for( final RowItem o : tblRefRackList.getItems()) {
                if( o.statusProperty().get()) {
                    st.add( o );
                }
            }
            tblRefRackList.getItems().removeAll( st );
        });


        tblCells.setEditable(true);
        tblRackRefList.setEditable(true);
        tblRefRackList.setEditable(true);
    }


    @FXML
    private void initReferenceTab(){
        refreshCmbReference();
        selectReference();
    }

    private void refreshCmbReference() {
        cmbReference.getItems().clear();
        referenceList.stream().sorted().forEach(ref -> cmbReference.getItems().add(ref.getName()));
        cmbReference.getSelectionModel().select(0);
    }

    @FXML
    private void initRackTab(){
        refreshCmbRack();
        selectRack();
    }

    private void refreshCmbRack() {
        cmbRack.getItems().clear();
        rackList.stream().sorted().forEach(rack -> cmbRack.getItems().add(rack.getName()));

        cmbRack.getSelectionModel().select(0);
    }

    @FXML
    private void initUserTab(){
        refreshCmbUser();
        selectUser();
    }

    private void refreshCmbUser() {
        cmbUser.getItems().clear();
        userList.stream().sorted().forEach(user -> cmbUser.getItems().add(user.getUsername()));

        cmbUser.getSelectionModel().select(0);
    }

    @FXML
    private void selectUser(){
        txtFirstName.clear();
        txtLastName.clear();
        txtUsername.clear();
        txtEmail.clear();
        cmbRole.getSelectionModel().select(0);
        pswdPasword.clear();
        User user = userList.stream().filter(u -> u.getUsername().equals(cmbUser.getValue())).findAny().orElse(null);
        if(user != null) {
            txtFirstName.setText(user.getFirstName());
            txtLastName.setText(user.getLastName());
            txtUsername.setText(user.getUsername());
            txtEmail.setText(user.getEmail());

            int i = 0;
            switch (user.getRole_name()) {
                case UserRoles.ADMIN:
                    i = 1;
                    break; //index 1
                case UserRoles.MANAGER:
                    i = 2;
                    break; //index 2
                case UserRoles.KEEPER:
                    i = 3;
                    break; //index 3
                case UserRoles.DRIVER:
                    i = 4;
                    break; //index 4
            }
            cmbRole.getSelectionModel().select(i);

            pswdPasword.setText(user.getPassword());
        }
    }
    @FXML
    private void selectRack(){
        txtRackRow.clear();
        txtRackColumn.clear();
        tblRackRefList.getItems().clear();
        tblCells.getItems().clear();

        ObservableList<RowItem> items = FXCollections.observableArrayList();
        ObservableList<RowItem> itemsCell = FXCollections.observableArrayList();
        Rack rack = rackList.stream().filter(r -> r.getName().equals(cmbRack.getValue())).findAny().orElse(null);
        if(rack != null){
            txtRackRow.setText("" + rack.getRow());
            txtRackColumn.setText("" + rack.getCol());
            for( SapReference s: referenceList){
                boolean checkBox = false;
                if(s.getAllowedRackIds().length >0){
                    for (Long id: s.getAllowedRackIds()) {
                        if( id != null && id == rack.getId()){
                            checkBox = true;
                            break;
                        }
                    }
                }
                items.add(new RowItem(s.getName(),checkBox));
            }
            tblRackRefList.getItems().addAll(items);
            for (Cell cell: cellList) {
                if(cell.getAddress().startsWith(rack.getName())){
                    String s = cell.getAddress().split(":")[1];
                    itemsCell.add(new RowItem(s,cell.isBlocked()));
                }
            }
            tblCells.getItems().addAll(itemsCell);
        }
    }
    @FXML
    private void selectReference(){
        txtDescription.clear();
        cmbSize.setValue(0);
        tblRefRackList.getItems().clear();
        ObservableList<RowItem> items = FXCollections.observableArrayList();
        SapReference tmpRef = referenceList.stream().filter(ref -> ref.getName().equals(cmbReference.getValue())).findAny().orElse(null);
        if(tmpRef !=null) {
            txtDescription.setText(tmpRef.getDescription());
            cmbSize.setValue(tmpRef.getSize());
            for (Rack r: rackList) {
                boolean checkBox = false;
                if(tmpRef.getAllowedRackIds().length >0){
                    for (Long id: tmpRef.getAllowedRackIds()) {
                        if( id != null && id == r.getId()){
                            checkBox = true;
                            break;
                        }
                    }
                }
                items.add(new RowItem(r.getName(),checkBox));
            }
        }
        tblRefRackList.getItems().addAll(items);
    }

    private void makeTable(String selectedToggle) {
        tableData = new TableView();

        switch (selectedToggle){
            case "Materials":
                referenceList.clear();
                TableColumn<SapReference, String> ref1 = new TableColumn<>("Reference");
                ref1.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<SapReference, String> ref2 = new TableColumn<>("Size");
                ref2.setCellValueFactory(new PropertyValueFactory<>("size"));

                TableColumn<SapReference, String> ref3 = new TableColumn<>("Description");
                ref3.setCellValueFactory(new PropertyValueFactory<>("description"));

                TableColumn<SapReference, String> ref4 = new TableColumn<>("Allowed Racks");
                ref4.setCellValueFactory(new PropertyValueFactory<>("allowedRackIds"));

                tableData.getColumns().add(ref1);
                tableData.getColumns().add(ref2);
                tableData.getColumns().add(ref3);
                tableData.getColumns().add(ref4);
                if(!referenceList.isEmpty()) {
                    for (SapReference s : referenceList) {
                        tableData.getItems().add(s);
                    }
                }
                break;
            case "Racks":
                rackList.clear();
                TableColumn<Rack, String> rack1 = new TableColumn<>("Rack");
                rack1.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Rack, String> rack2 = new TableColumn<>("Column");
                rack2.setCellValueFactory(new PropertyValueFactory<>("col"));

                TableColumn<Rack, String> rack3 = new TableColumn<>("Row");
                rack3.setCellValueFactory(new PropertyValueFactory<>("row"));

                tableData.getColumns().add(rack1);
                tableData.getColumns().add(rack2);
                tableData.getColumns().add(rack3);


               // List<Rack> racks = GUI.controller.getBase().getRacks();
                if(!rackList.isEmpty()) {
                    for (Rack r : rackList) {
                        tableData.getItems().add(r);
                    }
                }
                break;
            case "Users":
                userList.clear();
                TableColumn<User, String> user1 = new TableColumn<>("First Name");
                user1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                TableColumn<User, String> user2 = new TableColumn<>("Last Name");
                user2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                TableColumn<User, String> user3 = new TableColumn<>("Email");
                user3.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<User, String> user4 = new TableColumn<>("Username");
                user4.setCellValueFactory(new PropertyValueFactory<>("username"));

                TableColumn<User, String> user5 = new TableColumn<>("Role");
                user5.setCellValueFactory(new PropertyValueFactory<>("role_name"));

                TableColumn<User, String> user6 = new TableColumn<>("Password");
                user6.setCellValueFactory(new PropertyValueFactory<>("password"));

                tableData.getColumns().add(user1);
                tableData.getColumns().add(user2);
                tableData.getColumns().add(user3);
                tableData.getColumns().add(user4);
                tableData.getColumns().add(user5);
                tableData.getColumns().add(user6);

                if(!userList.isEmpty()) {
                    for (User u : userList) {
                        tableData.getItems().add(u);
                    }
                }
                break;
            case "Cells":
                cellList.clear();
                TableColumn<models.Cell, String> cell1 = new TableColumn<>("Address");
                cell1.setCellValueFactory(new PropertyValueFactory<>("address"));

                TableColumn<models.Cell, String> cell2 = new TableColumn<>("row");
                cell2.setCellValueFactory(new PropertyValueFactory<>("row"));

                TableColumn<models.Cell, String> cell3 = new TableColumn<>("col");
                cell3.setCellValueFactory(new PropertyValueFactory<>("col"));

                TableColumn<models.Cell, String> cell4 = new TableColumn<>("Pallets");
                cell4.setCellValueFactory(new PropertyValueFactory<>("pallets"));

                TableColumn<models.Cell, String> cell5 = new TableColumn<>("isBlocked");
                cell5.setCellValueFactory(new PropertyValueFactory<>("blocked"));

                tableData.getColumns().add(cell1);
                tableData.getColumns().add(cell2);
                tableData.getColumns().add(cell3);
                tableData.getColumns().add(cell4);
                tableData.getColumns().add(cell5);


                if(!cellList.isEmpty()) {
                    for (Cell c : cellList) {
                        tableData.getItems().add(c);
                    }
                }
                break;
        }
        tableData.columnResizePolicyProperty().setValue(CONSTRAINED_RESIZE_POLICY);
        tableData.setPrefWidth(loadingPane.getWidth()-15);
        tableData.setPlaceholder(new Label("No rows to display"));
    }

    @FXML
    private void changeTableIm(){
        RadioButton selectedRadioButton = (RadioButton) loadingTypeIm.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        makeTable(toggleGroupValue);
       // loadingPane.getChildren().add(tableData);
        loadingPane.setContent(tableData);
    }
    @FXML
    private void changeTableEx(){
        RadioButton selectedRadioButton = (RadioButton) loadingTypeEx.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        makeTable(toggleGroupValue);
        unLoadingPane.setContent(tableData);
    }

    @FXML
    private void openFile(){

        FileChooser fc = new FileChooser();
        fc.setTitle("Select CSV to Import");
        File f = fc.showOpenDialog(GUI.primaryStage);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fc.getExtensionFilters().add(extFilter);

        BufferedReader bfr ;
        try{
            bfr = new BufferedReader(new FileReader(f));
            String line = bfr.readLine();
            while (line != null){
                String[] s = line.split(",");
                switch (f.getName()){
                    case "racks.csv":
                        Rack tmp = new Rack(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]));
                        rackList.add(tmp);
                        break;
                    case "users.csv":
                        User u = new User(s[2],s[0],s[1],s[4],s[3],s[5]);
                        userList.add(u);
                        break;
                    case "references.csv":
                        Long[] ids = new Long[s.length - 3];
                        List<Rack> racks = GUI.controller.getBase().getRacks();
                        int k =0;
                        for(int i = 3; i < s.length; i++){
                            for(Rack r: racks){
                                if(r.getName().equals(s[i])){
                                    ids[k++]= r.getId();
                                }
                            }
                        }
                        SapReference ref = new SapReference(s[0],Integer.parseInt(s[1]),s[2],ids);
                        referenceList.add(ref);
                        break;
                    case "cells.csv":
                        Cell c = new Cell(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                        if(s.length > 3) {
                            for(int i = 3; i < s.length;i++){
                                String reference = s[i];
                                int size = Integer.parseInt(s[i + 1]);
                                String s1 = s[i + 3];
                                LocalDateTime loadedDate = LocalDateTime .parse(s1);

                                int position = Integer.parseInt(s[i + 2]);
                                Pallet pallet = new Pallet(reference, size, convertToDate(loadedDate),position);
                                c.addPallet(pallet);
                                i = i + 3;
                            }
                        }
                        cellList.add(c);
                        break;
                }

                line = bfr.readLine();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        RadioButton selectedRadioButton = (RadioButton) loadingTypeIm.getSelectedToggle();
        if(selectedRadioButton != null) {
            String toggleGroupValue = selectedRadioButton.getText();
            tableData = new TableView();
            makeTable(toggleGroupValue);
            loadingPane.setContent(tableData);
        }
        //loadingPane.getChildren().add(tableData);
    }

    @FXML
    private void updateDB() {
        RadioButton selectedRadioButton = (RadioButton) loadingTypeIm.getSelectedToggle();
        if (selectedRadioButton != null) {
            switch (selectedRadioButton.getText()) {
                case "Materials":
                    if (!referenceList.isEmpty()) {
                        for (SapReference s : referenceList) {
                            GUI.controller.getBase().update(s);
                        }
                    }
                    break;
                case "Racks":
                    if (!rackList.isEmpty()) {
                        for (Rack r : rackList) {
                            GUI.controller.getBase().update(r);
                        }
                    }
                    break;
                case "Users":
                    if (!userList.isEmpty()) {
                        for (User u : userList) {
                            GUI.controller.getBase().update(u);
                        }
                    }
                    break;
                case "Cells":
                    if (!cellList.isEmpty()) {
                        for (Cell c : cellList) {
                            GUI.controller.getBase().update(c);
                        }
                    }
                    break;
            }

        }
    }
    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public Date convertToDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    private void updateReference(){

        List<Long> ids = new ArrayList<>();
        ObservableList<RowItem> items = tblRefRackList.getItems();
        SapReference reference = GUI.controller.getBase().getSapReferences().stream().filter(ref -> ref.getName().equals(cmbReference.getValue())).findAny().orElse(null);
        for (RowItem item: items) {
            if(item.statusProperty().getValue()) {

                ids.add(Objects.requireNonNull(rackList.stream().filter(rack -> rack.getName().equals(item.nameProperty().getValue())).findAny().orElse(null)).getId());
            }
        }
        Long[] allowedRackIds = new Long[ids.size()];
        for(int i = 0; i < ids.size();i++) allowedRackIds[i] = ids.get(i);
        SapReference tmpReference = new SapReference(cmbReference.getValue().toString(),Integer.parseInt(cmbSize.getValue().toString()),txtDescription.getText(), allowedRackIds);

        if(reference != null && !reference.equals(tmpReference)){
            tmpReference.setId(reference.getId());
            GUI.controller.getBase().update(tmpReference);
            GUI.controller.getBase().reloadTable(SapReference.class);
        }
        referenceList.clear();
        referenceList = GUI.controller.getBase().getSapReferences();
        refreshCmbReference();
    }
    @FXML
    private void updateRack(){

    }
    @FXML
    private void updateUser(){
        User user = GUI.controller.getBase().getUsers().stream().filter(u -> u.getUsername().equals(cmbUser.getValue())).findAny().orElse(null);
        User tmpUser = new User(txtUsername.getText(),txtFirstName.getText(),txtLastName.getText(),txtEmail.getText(),cmbRole.getValue().toString(),pswdPasword.getText());
        if(user != null && !user.equals(tmpUser)){
            tmpUser.setId(user.getId());
            GUI.controller.getBase().update(tmpUser);
            GUI.controller.getBase().reloadTable(User.class);
        }
        userList.clear();
        userList = GUI.controller.getBase().getUsers();
        refreshCmbUser();
    }
    @FXML
    private void newReference(){

    }
    @FXML
    private void newRack(){

    }
    @FXML
    private void newUser(){

    }
    @FXML
    private void deleteReference(){
        SapReference reference = referenceList.stream().filter(sapReference -> sapReference.getName().equals(cmbReference.getValue())).findAny().orElse(null);
        if(reference != null){
            GUI.controller.getBase().removeById(SapReference.class,reference.getId());
            GUI.controller.getBase().reloadTable(SapReference.class);
        }
        referenceList.clear();
        referenceList = GUI.controller.getBase().getSapReferences();
        refreshCmbReference();
    }
    @FXML
    private void deleteRack(){
        Rack rack = rackList.stream().filter(r -> r.getName().equals(cmbRack.getValue())).findAny().orElse(null);
        if(rack != null){
            GUI.controller.getBase().removeById(Rack.class,rack.getId());
            GUI.controller.getBase().reloadTable(Rack.class);
            List<Cell> cells = GUI.controller.getBase().getCells().stream().filter(cell -> cell.getAddress().split(":")[0].equals(rack.getName())).toList();
            for (Cell c:cells
                 ) {
                GUI.controller.getBase().removeById(Cell.class,c.getId());
            }
            GUI.controller.getBase().reloadTable(Cell.class);
        }
        rackList.clear();
        rackList = GUI.controller.getBase().getRacks();
        refreshCmbRack();
    }
    @FXML
    private void deleteUser(){
        User user = userList.stream().filter(u -> u.getUsername().equals(cmbUser.getValue())).findAny().orElse(null);
        if(user != null){
            GUI.controller.getBase().removeById(User.class,user.getId());
            GUI.controller.getBase().reloadTable(User.class);
        }
        userList.clear();
        userList = GUI.controller.getBase().getUsers();
        refreshCmbUser();
    }


    public class RowItem {

        private final StringProperty name   = new SimpleStringProperty();
        private final BooleanProperty status = new SimpleBooleanProperty();

        public RowItem( String nm, boolean st ) {
            name  .set( nm  );
            status.set( st );
        }

        public StringProperty  nameProperty  () { return name;   }
        public BooleanProperty statusProperty() { return status; }
    }
}
