package com.hms.hospitalmanagementsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorMainFormController implements Initializable {

    @FXML
    private TextArea appointment_address;

    @FXML
    private TextField appointment_appointmentID;

    @FXML
    private Button appointment_clearBtn;

    @FXML
    private Button appointment_deleteBtn;

    @FXML
    private TextField appointment_description;

    @FXML
    private TextField appointment_diagnosis;

    @FXML
    private ComboBox<String> appointment_gender;

    @FXML
    private Button appointment_insertBtn;

    @FXML
    private TextField appointment_mobileNumber;

    @FXML
    private TextField appointment_name;

    @FXML
    private ComboBox<String> appointment_status;

    @FXML
    private TextField appointment_treatment;

    @FXML
    private Button appointment_updateBtn;

    @FXML
    private Button appointments_btn;

    @FXML
    private TableView<AppointmentData> appointments_tableView;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_action;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_appointmentID;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_contactNumber;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_date;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_dateDelete;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_dateModify;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_description;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_gender;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_name;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_status;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private Label current_form;

    @FXML
    private Label dashboard_AD;

    @FXML
    private Label dashboard_AP;

    @FXML
    private Label dashboard_TA;

    @FXML
    private Label dashboard_TP;

    @FXML
    private Button dashboard_btn;

    @FXML
    private BarChart<?, ?> dashboard_chart_DD;

    @FXML
    private AreaChart<?, ?> dashboard_chart_PD;

    @FXML
    private TableColumn<?, ?> dashboard_col_doctorID;

    @FXML
    private TableColumn<?, ?> dashboard_col_name;

    @FXML
    private TableColumn<?, ?> dashboard_col_specialized;

    @FXML
    private TableColumn<?, ?> dashboard_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TableView<?> dashboard_tableView;

    @FXML
    private Label date_time;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label nav_adminID;

    @FXML
    private Label nav_username;

    @FXML
    private Label patients_PA_dateCreated;

    @FXML
    private Label patients_PA_password;

    @FXML
    private Label patients_PA_patientID;

    @FXML
    private Button patients_PI_addBtn;

    @FXML
    private Label patients_PI_address;

    @FXML
    private Label patients_PI_gender;

    @FXML
    private Label patients_PI_mobileNumber;

    @FXML
    private Label patients_PI_patientName;

    @FXML
    private Button patients_PI_recordBtn;

    @FXML
    private TextArea patients_address;

    @FXML
    private Button patients_btn;

    @FXML
    private Button patients_confirmBtn;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private ComboBox<String> patients_gender;

    @FXML
    private TextField patients_mobileNumber;

    @FXML
    private TextField patients_password;

    @FXML
    private TextField patients_patientID;

    @FXML
    private TextField patients_patientName;

    @FXML
    private Button profile_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;

    @FXML
    private DatePicker appointment_schedule;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet rs;
    private final AlertMessage alert = new AlertMessage();

    public void patientConfirmBtn() {

        if (patients_patientID.getText().isEmpty()
                || patients_patientName.getText().isEmpty()
                || patients_gender.getSelectionModel().getSelectedItem() == null
                || patients_mobileNumber.getText().isEmpty()
                || patients_password.getText().isEmpty()
                || patients_address.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields!");
        } else {

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            patients_PA_patientID.setText(patients_patientID.getText());
            patients_PA_password.setText(patients_password.getText());
            patients_PA_dateCreated.setText(String.valueOf(sqlDate));

            patients_PI_patientName.setText(patients_patientName.getText());
            patients_PI_gender.setText(patients_gender.getSelectionModel().getSelectedItem());
            patients_PI_mobileNumber.setText(patients_mobileNumber.getText());
            patients_PI_address.setText(patients_address.getText());

        }
    }

    public void patientAddBtn() {

        if (patients_PA_patientID.getText().isEmpty()
                || patients_PA_password.getText().isEmpty()
                || patients_PA_dateCreated.getText().isEmpty()
                || patients_PI_patientName.getText().isEmpty()
                || patients_PI_gender.getText().isEmpty()
                || patients_PI_mobileNumber.getText().isEmpty()
                || patients_PI_address.getText().isEmpty()) {
            alert.errorMessage("Something went wrong");
        } else {

            Database.connectDB();

            try {
                String doctorName = "";
                String doctorSpecialized = "";

                String getDoctor = "SELECT * FROM doctor WHERE doctor_id='"
                        + nav_adminID.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(getDoctor);

                if (rs.next()) {
                    doctorName = rs.getString("full_name");
                    doctorSpecialized = rs.getString("specialized");
                }

                String checkPatientID = "SELECT * FROM patient WHERE patient_id='"
                        + patients_PA_patientID.getText() + "'";

                statement = connect.createStatement();
                rs = statement.executeQuery(checkPatientID);

                if (rs.next()) {
                    alert.errorMessage(patients_PA_patientID.getText() + "already exist");
                } else {
                    String insertData = "INSERT INTO patient(patient_id,password,full_name,mobile_number," +
                            "address,doctor,specialized,date," +
                            "status) VALUES(?,?,?,?,?,?,?,?,?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare = connect.prepareStatement(insertData);

                    prepare.setString(1, patients_PA_patientID.getText());
                    prepare.setString(2, patients_PA_password.getText());
                    prepare.setString(3, patients_PI_patientName.getText());
                    prepare.setString(4, patients_PI_mobileNumber.getText());
                    prepare.setString(5, patients_PI_address.getText());
                    prepare.setString(6, nav_adminID.getText());
                    prepare.setString(7, doctorSpecialized);
                    prepare.setString(8, "" + sqlDate);
                    prepare.setString(9, "Confirm");

                    prepare.executeUpdate();

                    alert.successMessage("Added successfully!");

                    patientClearFields();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void patientRecordBtn() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("RecordPageForm.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Hospital Management System | Record of Patients");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void patientClearFields() {

        patients_patientID.clear();
        patients_patientName.clear();
        patients_gender.getSelectionModel().clearSelection();
        patients_mobileNumber.clear();
        patients_password.clear();
        patients_address.clear();

        patients_PA_patientID.setText("");
        patients_PA_password.setText("");
        patients_PA_dateCreated.setText("");

        patients_PI_patientName.setText("");
        patients_PI_gender.setText("");
        patients_PI_mobileNumber.setText("");
        patients_PI_address.setText("");

    }

    private void patientGenderList() {

        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableList(listG);
        patients_gender.setItems(listData);

    }

    public void appointmentInsertBtn() {

        if (appointment_appointmentID.getText().isEmpty()
                || appointment_name.getText().isEmpty()
                || appointment_gender.getSelectionModel().getSelectedItem() == null
                || appointment_description.getText().isEmpty()
                || appointment_mobileNumber.getText().isEmpty()
                || appointment_address.getText().isEmpty()
                || appointment_status.getSelectionModel().getSelectedItem() == null
                || appointment_schedule.getValue() == null) {
            alert.errorMessage("Please fill all  blank fields!");
        } else {
            String checkAppointmentID = "SELECT * FROM appointment WHERE appointment_id ='"
                    + appointment_appointmentID.getText() + "'";
            connect = Database.connectDB();

            try {
                statement = connect.createStatement();
                rs = statement.executeQuery(checkAppointmentID);

                if (rs.next()) {
                    alert.errorMessage(appointment_appointmentID + "already taken");
                } else {
                    String getSpecialized = "";
                    String getDoctorData = "SELECT * FROM doctor WHERE doctor_id ='" + Data.doctor_id + "'";

                    statement = connect.createStatement();
                    rs = statement.executeQuery(getDoctorData);

                    if (rs.next()) {
                        getSpecialized = rs.getString("specialized");
                    }

                    String insertData = "INSERT INTO appointment(appointment_id,name,gender," +
                            "description,diagnosis,treatment,mobile_number,address," +
                            "date,status,doctor,specialized,schedule) " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);

                    prepare.setString(1, appointment_appointmentID.getText());
                    prepare.setString(2, appointment_name.getText());
                    prepare.setString(3, (String) appointment_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(4, appointment_description.getText());
                    prepare.setString(5, appointment_diagnosis.getText());
                    prepare.setString(6, appointment_treatment.getText());
                    prepare.setString(7, appointment_mobileNumber.getText());
                    prepare.setString(8, appointment_address.getText());

                    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
                    prepare.setString(9, "" + sqlDate);
                    prepare.setString(10, (String) appointment_status.getSelectionModel().getSelectedItem());
                    prepare.setString(11, Data.doctor_id);
                    prepare.setString(12, getSpecialized);
                    prepare.setString(13, "" + appointment_schedule.getValue());

                    prepare.executeUpdate();

                    appointmentShowData();
                    apppointmentAppointmentID();
                    appointmentClearBtn();

                    alert.successMessage("Successfully added!");

                }
                apppointmentAppointmentID();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void appointmentUpdateBtn() {

        if (appointment_appointmentID.getText().isEmpty()
                || appointment_name.getText().isEmpty()
                || appointment_gender.getSelectionModel().getSelectedItem() == null
                || appointment_description.getText().isEmpty()
                || appointment_mobileNumber.getText().isEmpty()
                || appointment_address.getText().isEmpty()
                || appointment_status.getSelectionModel().getSelectedItem() == null
                || appointment_schedule.getValue() == null) {
            alert.errorMessage("Please fill all  blank fields!");
        } else {

            java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

            String updateData = "UPDATE appointment SET name='"
                    + appointment_name.getText() + "', gender='"
                    + appointment_gender.getSelectionModel().getSelectedItem() + "', mobile_number='"
                    + appointment_mobileNumber.getText() + "',description='"
                    + appointment_description.getText() + "',address='"
                    + appointment_address.getText() + "',status='"
                    + appointment_status.getSelectionModel().getSelectedItem() + "',schedule='"
                    + appointment_schedule.getValue() + "', date_modify ='"
                    + sqlDate + "'WHERE appointment_id='"
                    + appointment_appointmentID.getText() + "'";

            connect = Database.connectDB();

            try {

                if (alert.confirmationMessage("Are you sure you want to UPDATE Appointment ID:"
                        + appointment_appointmentID.getText() + "?")) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    appointmentShowData();
                    apppointmentAppointmentID();
                    appointmentClearBtn();

                    alert.successMessage("Successfully Updated!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void appointmentDeleteBtn() {

        if (appointment_appointmentID.getText().isEmpty()) {
            alert.errorMessage("Please select  the item first!");
        } else {

            String updateData = "UPDATE appointment SET date_delete = ? WHERE appointment_id='"
                    + appointment_appointmentID.getText() + "'";

            connect = Database.connectDB();

            try {

                java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

                if (alert.confirmationMessage("Are yo sure you want to DELETE Appointment ID: "
                        + appointment_appointmentID.getText() + "?")) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    appointmentShowData();
                    apppointmentAppointmentID();
                    appointmentClearBtn();

                    alert.successMessage("Successfully Deleted!");
                } else {
                    alert.errorMessage("Cancelled.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void appointmentClearBtn() {

        appointment_appointmentID.clear();
        appointment_name.clear();
        appointment_gender.getSelectionModel().clearSelection();
        appointment_mobileNumber.clear();
        appointment_description.clear();
        appointment_diagnosis.clear();
        appointment_treatment.clear();
        appointment_address.clear();
        appointment_status.getSelectionModel().clearSelection();
        appointment_schedule.setValue(null);

        apppointmentAppointmentID();
    }

    private Integer appointmentID;

    public void apppointmentGetAppointmentID() {
        String sql = "SELECT MAX(appointment_id) FROM appointment";
        connect = Database.connectDB();

        int tempAppID = 0;

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempAppID = rs.getInt("MAX(appointment_id)");
            }

            if (tempAppID == 0) {
                tempAppID += 1;
            } else {
                tempAppID += 1;
            }

            appointmentID = tempAppID;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void apppointmentAppointmentID() {
        apppointmentGetAppointmentID();

        appointment_appointmentID.setText("" + appointmentID);
        appointment_appointmentID.setDisable(true);
    }

    public void appointmentGenderList() {
        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        appointment_gender.setItems(listData);
    }

    public void appointmentStatusList() {
        List<String> listS = new ArrayList<>();

        for (String data : Data.status) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        appointment_status.setItems(listData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {
        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();
        connect = Database.connectDB();

        String sql = "SELECT * FROM appointment WHERE  date_delete IS NULL";

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            AppointmentData appData;

            while (rs.next()) {

                appData = new AppointmentData(rs.getInt("appointment_id")
                        , rs.getString("name"), rs.getString("gender")
                        , rs.getString("description"), rs.getString("diagnosis")
                        , rs.getString("treatment"), rs.getLong("mobile_number")
                        , rs.getDate("date"), rs.getDate("date_modify")
                        , rs.getDate("date_delete"), rs.getString("status")
                        , rs.getString("address"), rs.getDate("schedule"));

                listData.add(appData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<AppointmentData> appointmentListData;

    public void appointmentShowData() {

        appointmentListData = appointmentGetData();

        appointments_col_appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointments_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        appointments_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        appointments_col_contactNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        appointments_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointments_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointments_col_dateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        appointments_col_dateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
        appointments_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointments_tableView.setItems(appointmentListData);

    }

    public void appointmentSelect() {
        AppointmentData appData = appointments_tableView.getSelectionModel().getSelectedItem();
        int num = appointments_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) return;

        appointment_appointmentID.setText("" + appData.getAppointmentID());
        appointment_name.setText(appData.getName());
        appointment_gender.getSelectionModel().select(appData.getGender());
        appointment_mobileNumber.setText("" + appData.getMobileNumber());
        appointment_description.setText("" + appData.getDescription());
        appointment_diagnosis.setText(appData.getDiagnosis());
        appointment_treatment.setText(appData.getTreatment());
        appointment_address.setText(appData.getAddress());
        appointment_status.getSelectionModel().select(appData.getStatus());
//        appointment_schedule.setValue(appData.getSchedule());

    }

    public void displayAdminIDNumberName() {

        String name = Data.doctor_name;
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        nav_username.setText(name);
        nav_adminID.setText(Data.doctor_id);
        top_username.setText(name);

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);
        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
        }
    }

    public void runTime() {

        new Thread() {

            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        date_time.setText(format.format(new Date()));
                    });
                }
            }

        }.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayAdminIDNumberName();
        runTime();

        appointmentShowData();
        appointmentGenderList();
        appointmentStatusList();
        apppointmentAppointmentID();

        patientGenderList();
    }
}
