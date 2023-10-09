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
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private Label dashboard_IP;

    @FXML
    private Label dashboard_IN;

    @FXML
    private Label dashboard_TA;

    @FXML
    private Label dashboard_TP;

    @FXML
    private Label dashboard_AP;

    @FXML
    private Button dashboard_btn;

    @FXML
    private BarChart<?, ?> dashboard_chart_NA;

    @FXML
    private AreaChart<?, ?> dashboard_chart_NP;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_appointmentDate;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_appointmentID;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_name;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_description;

    @FXML
    private TableColumn<AppointmentData, String> dashboard_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TableView<AppointmentData> dashboard_tableView;


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
    private Button logout_btn;

    @FXML
    private DatePicker appointment_schedule;

    @FXML
    private TextArea profile_address;

    @FXML
    private Circle profile_circleImage;

    @FXML
    private TextField profile_doctorID;

    @FXML
    private TextField profile_email;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private ComboBox<String> profile_gender;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private Label profile_label_doctorID;

    @FXML
    private Label profile_label_email;

    @FXML
    private Label profile_label_name;

    @FXML
    private TextField profile_mobileNumber;

    @FXML
    private TextField profile_name;

    @FXML
    private ComboBox<String> profile_specialized;

    @FXML
    private ComboBox<String> profile_status;

    @FXML
    private Button profile_updateBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet rs;

    private Image image;
    private final AlertMessage alert = new AlertMessage();

    public void dashboardDisplayIP() {
        String sql = "SELECT COUNT(id)  FROM patient WHERE status = 'Inactive ' AND doctor='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();
        int getIP = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                getIP = rs.getInt("COUNT(id)");
            }
            dashboard_TP.setText(String.valueOf(getIP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTP() {
        String sql = "SELECT COUNT(id)  FROM patient WHERE doctor='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();
        int getTP = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                getTP = rs.getInt("COUNT(id)");
            }
            dashboard_TP.setText(String.valueOf(getTP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayAP() {
        String sql = "SELECT COUNT(id)  FROM patient WHERE status = 'Active' AND doctor='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();
        int getAP = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                getAP = rs.getInt("COUNT(id)");
            }
            dashboard_AP.setText(String.valueOf(getAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTA() {
        String sql = "SELECT COUNT(id)  FROM appointment WHERE  status ='Active' AND doctor='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();
        int getTA = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                getTA = rs.getInt("COUNT(id)");
            }
            dashboard_TA.setText(String.valueOf(getTA));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AppointmentData> dashboardAppointmentTableView() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE doctor='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            AppointmentData appData;
            while (rs.next()) {
                appData = new AppointmentData(rs.getInt("appointment_id")
                        , rs.getString("name")
                        , rs.getString("description")
                        , rs.getDate("date"), rs.getString("status"));

                listData.add(appData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<AppointmentData> dashboardGetData;

    public void dashboardDisplayData() {
        dashboardGetData = dashboardAppointmentTableView();

        dashboard_col_appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        dashboard_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dashboard_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        dashboard_col_appointmentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        dashboard_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        dashboard_tableView.setItems(dashboardGetData);
    }


    public void dashboardNOP() {

        dashboard_chart_NP.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM patient WHERE doctor = '"
                + Data.doctor_id + "' GROUP BY date";

        connect = Database.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series<>();
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_NP.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardNOA() {

        dashboard_chart_NA.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM appointment WHERE doctor = '"
                + Data.doctor_id + "' GROUP BY date ";

        connect = Database.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series<>();
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_NA.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

        String sql = "SELECT * FROM appointment WHERE  date_delete IS NULL AND doctor='"
                + Data.doctor_id + "'";

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            AppointmentData appData;

            while (rs.next()) {

                appData = new AppointmentData(rs.getInt("id")
                        , rs.getInt("appointment_id")
                        , rs.getString("name")
                        , rs.getString("gender")
                        , rs.getLong("mobile_number")
                        , rs.getString("description")
                        , rs.getString("diagnosis")
                        , rs.getString("treatment")
                        , rs.getString("address")
                        , rs.getString("doctor")
                        , rs.getString("specialized")
                        , rs.getDate("date")
                        , rs.getDate("date_modify")
                        , rs.getDate("date_delete")
                        , rs.getString("status")
                        , rs.getDate("schedule"));

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
        appointment_description.setText(appData.getDescription());
        appointment_diagnosis.setText(appData.getDiagnosis());
        appointment_treatment.setText(appData.getTreatment());
        appointment_address.setText(appData.getAddress());
        appointment_status.getSelectionModel().select(appData.getStatus());
//        appointment_schedule.setValue(appData.getSchedule());

    }

    public void profileUpdateBtn() {

        connect = Database.connectDB();

        if (profile_doctorID.getText().isEmpty()
                || profile_name.getText().isEmpty()
                || profile_email.getText().isEmpty()
                || profile_gender.getSelectionModel().getSelectedItem() == null
                || profile_mobileNumber.getText().isEmpty()
                || profile_address.getText().isEmpty()
                || profile_specialized.getSelectionModel().getSelectedItem() == null
                || profile_status.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            if (Data.path == null || "".equals(Data.path)) {
                String updateData = "UPDATE doctor SET full_name = ?, email = ?," +
                        " gender = ?, mobile_number = ?, address = ?, specialized = ?, " +
                        "status = ?, modify_date = ? WHERE doctor_id='" + Data.doctor_id + "'";

                try {

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(updateData);

                    prepare.setString(1, profile_name.getText());
                    prepare.setString(2, profile_email.getText());
                    prepare.setString(3, profile_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(4, profile_mobileNumber.getText());
                    prepare.setString(5, profile_address.getText());
                    prepare.setString(6, profile_specialized.getSelectionModel().getSelectedItem());
                    prepare.setString(7, profile_status.getSelectionModel().getSelectedItem());
                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Profile Updated Successfully!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String updateData = "UPDATE doctor SET full_name = ?, email = ?," +
                        " gender = ?, mobile_number = ?, address = ?, image = ?, specialized = ?, " +
                        "status = ?, modify_date = ? WHERE doctor_id='" + Data.doctor_id + "'";

                try {

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(updateData);

                    prepare.setString(1, profile_name.getText());
                    prepare.setString(2, profile_email.getText());
                    prepare.setString(3, profile_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(4, profile_mobileNumber.getText());
                    prepare.setString(5, profile_address.getText());
                    String path = Data.path;
                    path = path.replace("\\", "\\\\");
                    Path transfer = Paths.get(path);


                    Path copy = Paths.get("C:\\Users\\EMMANUEL-YEGON\\hms-directory\\" + Data.doctor_id + ".jpg");

                    try {
                        //To put the image file to my directory folder
                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    prepare.setString(6, copy.toAbsolutePath().toString());
                    prepare.setString(7, profile_specialized.getSelectionModel().getSelectedItem());
                    prepare.setString(8, profile_status.getSelectionModel().getSelectedItem());
                    prepare.setString(9, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Profile Updated Successfully!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void changeProfile() {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*png", "*jpg", "*jpeg"));

        File file = open.showOpenDialog(profile_importBtn.getScene().getWindow());

        if (file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 130, 104, false, true);
            profile_circleImage.setFill(new ImagePattern(image));

        }
    }

    public void profileLabels() {
        String selectData = "SELECT * FROM doctor WHERE doctor_id='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            if (rs.next()) {
                profile_label_doctorID.setText(rs.getString("doctor_id"));
                profile_label_name.setText(rs.getString("full_name"));
                profile_label_email.setText(rs.getString("email"));
                profile_label_dateCreated.setText(rs.getString("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void profileFields() {

        String selectData = "SELECT * FROM doctor WHERE doctor_id='"
                + Data.doctor_id + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            if (rs.next()) {
                profile_doctorID.setText(rs.getString("doctor_id"));
                profile_name.setText(rs.getString("full_name"));
                profile_email.setText(rs.getString("email"));
                profile_gender.getSelectionModel().select(rs.getString("gender"));
                profile_mobileNumber.setText(rs.getString("mobile_number"));
                profile_address.setText(rs.getString("address"));
                profile_specialized.getSelectionModel().select(rs.getString("specialized"));
                profile_status.getSelectionModel().select(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileDisplayImages() {
        String selectData = "SELECT * FROM doctor WHERE doctor_id='"
                + Data.doctor_id + "'";

        String temp_path1 = "";
        String temp_path2 = "";
        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            if (rs.next()) {
                temp_path1 = "File:" + rs.getString("image");
                temp_path2 = "File:" + rs.getString("image");

                if (rs.getString("image") != null) {
                    image = new Image(temp_path1, 1014, 20, false, true);
                    top_profile.setFill(new ImagePattern(image));

                    image = new Image(temp_path2, 130, 104, false, true);
                    profile_circleImage.setFill(new ImagePattern(image));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : Data.gender) {
            genderL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(genderL);
        profile_gender.setItems(listData);
    }

    private String[] specialization = {"Allergist", "Gynecologist", "Psychiatrists", "Cardiologist", "Dermatologists"};

    public void profileSpecializedList() {
        List<String> specializedL = new ArrayList<>();

        for (String data : specialization) {
            specializedL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(specializedL);
        profile_specialized.setItems(listData);
    }

    public void profileStatusList() {
        List<String> statusL = new ArrayList<>();

        for (String data : Data.status) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        profile_status.setItems(listData);
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
            profile_form.setVisible(false);
        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
            profile_form.setVisible(false);
        } else if (event.getSource() == profile_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(true);
        }
    }


    public void logoutBtn() {

        try {
            if (alert.confirmationMessage("Are you sure you want to logout?")) {
                Data.doctor_id = "";
                Data.doctor_name = "";
                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                logout_btn.getScene().getWindow().hide();

                Data.doctor_id = "";
                Data.doctor_name = "";
                Data.temp_patientID = 0;
                Data.temp_name = "";
                Data.temp_gender = "";
                Data.temp_number = Long.parseLong("0");
                Data.temp_address = "";
                Data.temp_status = "";
                Data.temp_date = "";
                Data.temp_path = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
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

        dashboardDisplayIP();
        dashboardDisplayAP();
        dashboardDisplayTP();
        dashboardDisplayTA();
        dashboardDisplayData();
        dashboardNOP();
        dashboardNOA();

        appointmentShowData();
        appointmentGenderList();
        appointmentStatusList();
        apppointmentAppointmentID();

        patientGenderList();

        profileLabels();
        profileFields();
        profileGenderList();
        profileSpecializedList();
        profileStatusList();
        profileDisplayImages();

    }
}
