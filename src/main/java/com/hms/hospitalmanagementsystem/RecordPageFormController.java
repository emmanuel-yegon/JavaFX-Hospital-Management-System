package com.hms.hospitalmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RecordPageFormController implements Initializable {

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_action;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_address;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_dateCreated;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_dateDeleted;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_dateModified;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_gender;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_mobileNumber;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_name;

    @FXML
    private TableColumn<PatientsData,String> recordpage_col_patientID;

    @FXML
    private AnchorPane recordpage_mainForm;

    @FXML
    private TextField recordpage_search;

    @FXML
    private TableView<PatientsData> recordpage_tableView;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet rs;

    public ObservableList<PatientsData> getPatientRecordData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM patient WHERE date_delete IS NULL AND doctor ='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            PatientsData pData;

            while (rs.next()) {
                pData = new PatientsData(rs.getInt("id"), rs.getInt("patient_id")
                        , rs.getString("full_name"), rs.getLong("mobile_number")
                        , rs.getString("address"), rs.getDate("date")
                        , rs.getDate("date_modify"), rs.getDate("date_delete"));
                listData.add(pData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<PatientsData> patientRecordData;

    public void displayPatientsData(){
        patientRecordData = getPatientRecordData();

        recordpage_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        recordpage_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        recordpage_col_mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        recordpage_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        recordpage_col_dateCreated.setCellValueFactory(new PropertyValueFactory<>("date"));
        recordpage_col_dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        recordpage_col_dateDeleted.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));

        recordpage_tableView.setItems(patientRecordData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayPatientsData();
    }

}
