package com.hms.hospitalmanagementsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

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
    private ComboBox<?> appointment_gender;

    @FXML
    private Button appointment_insertBtn;

    @FXML
    private TextField appointment_mobileNumber;

    @FXML
    private TextField appointment_name;

    @FXML
    private ComboBox<?> appointment_status;

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
    private ComboBox<?> patients_gender;

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
        }
        else {
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

                    alert.successMessage("Successfully added!");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                        , rs.getString("name")
                        , rs.getString("gender")
                        , rs.getString("description")
                        , rs.getLong("mobile_number")
                        , rs.getDate("date")
                        , rs.getDate("date_modify")
                        , rs.getDate("date_delete")
                        , rs.getString("status"));

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
    }
}
