package com.hms.hospitalmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditAppointmentFormController implements Initializable {

    @FXML
    private TextArea editApp_address;

    @FXML
    private TextField editApp_appointmentID;

    @FXML
    private Button editApp_cancelBtn;

    @FXML
    private TextArea editApp_description;

    @FXML
    private TextField editApp_diagnosis;

    @FXML
    private ComboBox<String> editApp_doctor;

    @FXML
    private TextField editApp_fullName;

    @FXML
    private ComboBox<String> editApp_gender;

    @FXML
    private TextField editApp_mobileNumber;

    @FXML
    private ComboBox<String> editApp_specialized;

    @FXML
    private ComboBox<String> editApp_status;

    @FXML
    private TextField editApp_treatment;

    @FXML
    private Button editApp_updateBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement statement;


    public void displayFields(){

        editApp_appointmentID.setText(Data.temp_appID);
        editApp_fullName.setText(Data.temp_appName);
        editApp_gender.getSelectionModel().select(Data.temp_appGender);
        editApp_mobileNumber.setText(Data.temp_appMobileNumber);
        editApp_address.setText(Data.temp_appAddress);
        editApp_description.setText(Data.temp_appDescription);
        editApp_diagnosis.setText(Data.temp_appDiagnosis);
        editApp_treatment.setText(Data.temp_appTreatment);
        editApp_doctor.getSelectionModel().select(Data.temp_appDoctor);
        editApp_specialized.getSelectionModel().select(Data.temp_appSpecialized);
        editApp_status.getSelectionModel().select(Data.temp_appStatus);

    }

    public void genderList() {

        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        editApp_gender.setItems(listData);
    }

    public void doctorList() {
        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();

            while (rs.next()) {
                listData.add(rs.getString("doctor_id"));
            }

            editApp_doctor.setItems(listData);
            specializedList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void specializedList() {
        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL AND doctor_id='"
                + editApp_doctor.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (rs.next()) {
                listData.add(rs.getString("specialized"));
            }
            editApp_specialized.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void statusList() {

        List<String> listS = new ArrayList<>();

        for (String data : Data.status) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        editApp_status.setItems(listData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doctorList();
        genderList();
        statusList();

        displayFields();
    }
}
