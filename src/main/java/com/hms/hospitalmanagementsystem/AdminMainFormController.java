package com.hms.hospitalmanagementsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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

public class AdminMainFormController implements Initializable {

    public ObservableList<PatientsData> patientListData;

    @FXML
    private TableColumn<AppointmentData, String> appointments_action;
    @FXML
    private TableColumn<AppointmentData, String> appointments_appointmentID;
    @FXML
    private Button appointments_btn;
    @FXML
    private TableColumn<AppointmentData, String> appointments_contact;
    @FXML
    private TableColumn<AppointmentData, String> appointments_date;
    @FXML
    private TableColumn<AppointmentData, String> appointments_dateDelete;
    @FXML
    private TableColumn<AppointmentData, String> appointments_dateModify;
    @FXML
    private TableColumn<AppointmentData, String> appointments_description;
    @FXML
    private AnchorPane appointments_form;
    @FXML
    private TableColumn<AppointmentData, String> appointments_gender;
    @FXML
    private TableColumn<AppointmentData, String> appointments_name;
    @FXML
    private TableColumn<AppointmentData, String> appointments_status;

    @FXML
    private TableView<AppointmentData> appointments_tableView;

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
    private TableColumn<DoctorData, String> dashboard_col_doctorID;
    @FXML
    private TableColumn<DoctorData, String> dashboard_col_name;
    @FXML
    private TableColumn<DoctorData, String> dashboard_col_specialized;
    @FXML
    private TableColumn<DoctorData, String> dashboard_col_status;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private TableView<DoctorData> dashboard_tableView;
    @FXML
    private Label date_time;
    @FXML
    private Button doctors_btn;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_action;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_address;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_contactNumber;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_doctorID;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_email;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_gender;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_name;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_specialization;
    @FXML
    private TableColumn<DoctorData, String> doctors_col_status;
    @FXML
    private AnchorPane doctors_form;
    @FXML
    private TableView<DoctorData> doctors_tableView;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Label nav_adminID;
    @FXML
    private Label nav_username;
    @FXML
    private Button patients_btn;
    @FXML
    private TableColumn<PatientsData, String> patients_col_action;
    @FXML
    private TableColumn<PatientsData, String> patients_col_contactNumber;
    @FXML
    private TableColumn<PatientsData, String> patients_col_date;
    @FXML
    private TableColumn<PatientsData, String> patients_col_dateDelete;
    @FXML
    private TableColumn<PatientsData, String> patients_col_dateModify;
    @FXML
    private TableColumn<PatientsData, String> patients_col_description;
    @FXML
    private TableColumn<PatientsData, String> patients_col_gender;
    @FXML
    private TableColumn<PatientsData, String> patients_col_name;
    @FXML
    private TableColumn<PatientsData, String> patients_col_patientID;
    @FXML
    private TableColumn<PatientsData, String> patients_col_status;
    @FXML
    private AnchorPane patients_form;
    @FXML
    private TableView<PatientsData> patients_tableView;


    @FXML
    private Button profile_btn;
    @FXML
    private Circle top_profile;
    @FXML
    private Label top_username;

    @FXML
    private TextField profile_adminID;

    @FXML
    private Circle profile_circle;

    @FXML
    private TextField profile_email;

    @FXML
    private TextField profile_fullName;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_adminID;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private Label profile_label_email;

    @FXML
    private Label profile_label_username;


    @FXML
    private ComboBox<String> profile_gender;

    @FXML
    private Button profile_updateBtn;

    @FXML
    private TextField profile_username;

    @FXML
    private Button payment_btn;

    @FXML
    private Button payment_checkOutBtn;

    @FXML
    private Circle payment_circle;

    @FXML
    private TableColumn<PatientsData, String> payment_col_date;

    @FXML
    private TableColumn<PatientsData, String> payment_col_diagnosis;

    @FXML
    private TableColumn<PatientsData, String> payment_col_doctor;

    @FXML
    private TableColumn<PatientsData, String> payment_col_gender;

    @FXML
    private TableColumn<PatientsData, String> payment_col_name;

    @FXML
    private TableColumn<PatientsData, String> payment_col_patientID;

    @FXML
    private Label payment_date;

    @FXML
    private Label payment_doctor;

    @FXML
    private AnchorPane payment_form;

    @FXML
    private Label payment_name;

    @FXML
    private Label payment_patientID;

    @FXML
    private TableView<PatientsData> payment_tableView;

    @FXML
    private Button logout_btn;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet rs;

    private final AlertMessage alert = new AlertMessage();

    private Image image;

    public void dashboardAD() {

        String sql = "SELECT COUNT(id) FROM doctor WHERE status='Active' AND date_delete IS NULL";

        connect = Database.connectDB();

        int tempAD = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempAD = rs.getInt("COUNT(id)");
            }
            dashboard_AD.setText(String.valueOf(tempAD));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE date_delete IS NULL";

        connect = Database.connectDB();

        int tempTP = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempTP = rs.getInt("COUNT(id)");
            }
            dashboard_TP.setText(String.valueOf(tempTP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardAP() {

        String sql = "SELECT COUNT(id) FROM patient WHERE date_delete IS NULL AND status = 'Active'";

        connect = Database.connectDB();

        int tempAP = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempAP = rs.getInt("COUNT(id)");
            }
            dashboard_AP.setText(String.valueOf(tempAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTA() {

        String sql = "SELECT COUNT(id) FROM appointment WHERE date_delete IS NULL";

        connect = Database.connectDB();

        int tempTA = 0;
        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempTA = rs.getInt("COUNT(id)");
            }
            dashboard_TA.setText(String.valueOf(tempTA));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DoctorData> dashboardGetDoctorData() {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            DoctorData dData;

            while (rs.next()) {
                dData = new DoctorData(rs.getString("doctor_id")
                        , rs.getString("full_name")
                        , rs.getString("specialized")
                        , rs.getString("status"));
                listData.add(dData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<DoctorData> dashboardGetDoctorListData;

    public void dashboardGetDoctorDisplayData() {
        dashboardGetDoctorListData = dashboardGetDoctorData();

        dashboard_col_doctorID.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        dashboard_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dashboard_col_specialized.setCellValueFactory(new PropertyValueFactory<>("specialized"));
        dashboard_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        dashboard_tableView.setItems(dashboardGetDoctorListData);
    }

    public void dashboardPatientDataChart() {

        dashboard_chart_PD.getData().clear();

        String selectData = "SELECT date ,COUNT(id) FROM patient WHERE date_delete IS NULL GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";

        connect = Database.connectDB();
        XYChart.Series chart = new XYChart.Series<>();

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_PD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardDoctorDataChart() {

        dashboard_chart_DD.getData().clear();

        String selectData = "SELECT date, COUNT(id) FROM doctor WHERE date_delete IS NULL  GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7 ";

        connect = Database.connectDB();
        XYChart.Series chart = new XYChart.Series<>();

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            while (rs.next()) {
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }

            dashboard_chart_DD.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<DoctorData> doctorListData;

    public ObservableList<DoctorData> doctorGetData() {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM doctor";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();
            DoctorData dData;

            while (rs.next()) {

                dData = new DoctorData(rs.getInt("id"), rs.getString("doctor_id")
                        , rs.getString("password"), rs.getString("full_name")
                        , rs.getString("email"), rs.getString("gender")
                        , rs.getLong("mobile_number"), rs.getString("specialized")
                        , rs.getString("address"), rs.getString("image")
                        , rs.getDate("date"), rs.getDate("date_modify")
                        , rs.getDate("date_delete"), rs.getString("status"));

                listData.add(dData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void doctorDisplayData() {
        doctorListData = doctorGetData();

        doctors_col_doctorID.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        doctors_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        doctors_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        doctors_col_contactNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        doctors_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        doctors_col_specialization.setCellValueFactory(new PropertyValueFactory<>("specialized"));
        doctors_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        doctors_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        doctors_tableView.setItems(doctorListData);

    }

    public void doctorActionButtons() {

        connect = Database.connectDB();
        doctorListData = doctorGetData();

        Callback<TableColumn<DoctorData, String>, TableCell<DoctorData, String>> cellFactory = (TableColumn<DoctorData, String> param) -> {
            final TableCell<DoctorData, String> cell = new TableCell<DoctorData, String>() {
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button editButton = new Button("Edit");
                        Button removeButton = new Button("Delete");

                        editButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        removeButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        editButton.setOnAction((ActionEvent event) -> {
                            try {
                                connect = Database.connectDB();
                                DoctorData dData = doctors_tableView.getSelectionModel().getSelectedItem();
                                int num = doctors_tableView.getSelectionModel().getSelectedIndex();

                                if ((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                }

                                Data.temp_doctorID = dData.getDoctorID();
                                Data.temp_doctorName = dData.getFullName();
                                Data.temp_doctorEmail = dData.getEmail();
                                Data.temp_doctorPassword = dData.getPassword();
                                Data.temp_doctorSpecialized = dData.getSpecialized();
                                Data.temp_doctorGender = dData.getGender();
                                Data.temp_doctorMobileNumber = dData.getMobileNumber();
                                Data.temp_doctorAddress = dData.getAddress();
                                Data.temp_doctorStatus = dData.getStatus();
                                Data.temp_doctorImagePath = dData.getImage();


                                Parent root = FXMLLoader.load(getClass().getResource("EditDoctorForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction((ActionEvent evemt) -> {
                            connect = Database.connectDB();
                            DoctorData dData = doctors_tableView.getSelectionModel().getSelectedItem();
                            int num = doctors_tableView.getSelectionModel().getSelectedIndex();

                            if ((num - 1) < -1) {
                                alert.errorMessage("Please select item first");
                            }

                            String deleteData = "UPDATE doctor SET date_delete = ? WHERE doctor_id='"
                                    + dData.getDoctorID() + "'";

                            try {
                                if (alert.confirmationMessage("Are you sure you want to delete Doctor ID: " + dData.getDoctorID() + "?")) {
                                    prepare = connect.prepareStatement(deleteData);
                                    Date date = new Date();
                                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                                    prepare.setString(1, String.valueOf(sqlDate));
                                    prepare.executeUpdate();


                                    alert.successMessage("Deleted successfully!");
                                    doctorDisplayData();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox manageBtn = new HBox(editButton, removeButton);
                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(5);
                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };
            doctorDisplayData();
            return cell;
        };
        doctors_col_action.setCellFactory(cellFactory);
        doctors_tableView.setItems(doctorListData);
    }

    public void patientActionButtons() {

        connect = Database.connectDB();
        patientListData = patientGetData();

        Callback<TableColumn<PatientsData, String>, TableCell<PatientsData, String>> cellFactory = (TableColumn<PatientsData, String> param) -> {
            final TableCell<PatientsData, String> cell = new TableCell<PatientsData, String>() {
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button editButton = new Button("Edit");
                        Button removeButton = new Button("Delete");

                        editButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        removeButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        editButton.setOnAction((ActionEvent event) -> {
                            try {
                                connect = Database.connectDB();
                                PatientsData pData = patients_tableView.getSelectionModel().getSelectedItem();
                                int num = patients_tableView.getSelectionModel().getSelectedIndex();

                                if ((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                }

                                Data.temp_patientID = pData.getPatientID();
                                Data.temp_address = pData.getAddress();
                                Data.temp_name = pData.getFullName();
                                Data.temp_gender = pData.getGender();
                                Data.temp_number = pData.getMobileNumber();
                                Data.temp_status = pData.getStatus();


                                Parent root = FXMLLoader.load(getClass().getResource("EditPatientForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction((ActionEvent evemt) -> {
                            connect = Database.connectDB();
                            PatientsData pData = patients_tableView.getSelectionModel().getSelectedItem();
                            int num = patients_tableView.getSelectionModel().getSelectedIndex();

                            if ((num - 1) < -1) {
                                alert.errorMessage("Please select item first");
                            }

                            String deleteData = "UPDATE patient SET date_delete = ? WHERE patient_id='"
                                    + pData.getPatientID() + "'";

                            try {
                                if (alert.confirmationMessage("Are you sure you want to delete Patient ID: " + pData.getPatientID() + "?")) {
                                    prepare = connect.prepareStatement(deleteData);
                                    Date date = new Date();
                                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                                    prepare.setString(1, String.valueOf(sqlDate));
                                    prepare.executeUpdate();


                                    alert.successMessage("Deleted successfully!");
                                    patientDisplayData();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox manageBtn = new HBox(editButton, removeButton);
                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(5);
                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };
            patientDisplayData();
            return cell;
        };
        patients_col_action.setCellFactory(cellFactory);
        patients_tableView.setItems(patientListData);
    }

    public ObservableList<PatientsData> patientGetData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM patient";

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            PatientsData pData;

            while (rs.next()) {
                pData = new PatientsData(rs.getInt("id"), rs.getInt("patient_id")
                        , rs.getString("password"), rs.getString("full_name")
                        , rs.getString("gender")
                        , rs.getLong("mobile_number"), rs.getString("address")
                        , rs.getString("image"), rs.getString("description")
                        , rs.getString("diagnosis"), rs.getString("treatment")
                        , rs.getString("doctor"), rs.getString("specialized")
                        , rs.getDate("date"), rs.getDate("date_modify")
                        , rs.getDate("date_delete"), rs.getString("status"));

                listData.add(pData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void patientDisplayData() {
        patientListData = patientGetData();

        patients_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        patients_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        patients_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patients_col_contactNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        patients_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        patients_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        patients_col_dateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        patients_col_dateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
        patients_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        patients_tableView.setItems(patientListData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            AppointmentData aData;

            while (rs.next()) {
                aData = new AppointmentData(rs.getInt("id")
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

                listData.add(aData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<AppointmentData> appointmentListData;

    public void appointmentDisplayData() {
        appointmentListData = appointmentGetData();

        appointments_appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointments_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        appointments_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        appointments_contact.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        appointments_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointments_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointments_dateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        appointments_dateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
        appointments_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointments_tableView.setItems(appointmentListData);

    }

    public void appointmentActionButtons() {
        connect = Database.connectDB();
        appointmentListData = appointmentGetData();

        Callback<TableColumn<AppointmentData, String>, TableCell<AppointmentData, String>> cellFactory = (TableColumn<AppointmentData, String> param) -> {
            final TableCell<AppointmentData, String> cell = new TableCell<AppointmentData, String>() {
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button editButton = new Button("Edit");
                        Button removeButton = new Button("Delete");

                        editButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        removeButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #188ba7, #306090);\n" +
                                "   -fx-cursor: hand;\n" +
                                "   -fx-text-fill: #fff;\n" +
                                "   -fx-font-size: 14px;\n" +
                                "   -fx-font-family: Arial;");

                        editButton.setOnAction((ActionEvent event) -> {
                            try {

                                connect = Database.connectDB();
                                AppointmentData aData = appointments_tableView.getSelectionModel().getSelectedItem();
                                int num = appointments_tableView.getSelectionModel().getSelectedIndex();

                                if ((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                }

                                Data.temp_appID = String.valueOf(aData.getAppointmentID());
                                Data.temp_appName = aData.getName();
                                Data.temp_appGender = aData.getGender();
                                Data.temp_appAddress = aData.getAddress();
                                Data.temp_appDescription = aData.getDescription();
                                Data.temp_appDiagnosis = aData.getDiagnosis();
                                Data.temp_appTreatment = aData.getTreatment();
                                Data.temp_appMobileNumber = String.valueOf(aData.getMobileNumber());
                                Data.temp_appDoctor = aData.getDoctorID();
                                Data.temp_appSpecialized = aData.getSpecialized();
                                Data.temp_appStatus = aData.getStatus();


                                Parent root = FXMLLoader.load(getClass().getResource("EditAppointmentForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction((ActionEvent evemt) -> {
                            connect = Database.connectDB();
                            AppointmentData aData = appointments_tableView.getSelectionModel().getSelectedItem();
                            int num = appointments_tableView.getSelectionModel().getSelectedIndex();

                            if ((num - 1) < -1) {
                                alert.errorMessage("Please select item first");
                            }

                            String deleteData = "UPDATE  appointment SET date_delete = ? WHERE appointment_id='"
                                    + aData.getAppointmentID() + "'";

                            try {
                                if (alert.confirmationMessage("Are you sure you want to delete Appointment ID: " + aData.getAppointmentID() + "?")) {
                                    prepare = connect.prepareStatement(deleteData);
                                    Date date = new Date();
                                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                                    prepare.setString(1, String.valueOf(sqlDate));
                                    prepare.executeUpdate();


                                    alert.successMessage("Deleted successfully!");
                                    appointmentDisplayData();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox manageBtn = new HBox(editButton, removeButton);
                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(5);
                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };
            appointmentDisplayData();
            return cell;
        };
        appointments_action.setCellFactory(cellFactory);
        appointments_tableView.setItems(appointmentListData);
    }

    public ObservableList<PatientsData> paymentGetData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM patient WHERE date_delete IS NULL AND status_pay IS NULL";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            PatientsData pData;

            while (rs.next()) {
                pData = new PatientsData(rs.getInt("id"), rs.getInt("patient_id")
                        , rs.getString("full_name"), rs.getString("gender")
                        , rs.getString("description"), rs.getString("diagnosis")
                        , rs.getString("treatment"), rs.getString("image")
                        , rs.getString("doctor"), rs.getDate("date"));

                listData.add(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<PatientsData> paymentListData;

    public void paymentDisplayData() {

        patientListData = paymentGetData();

        payment_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        payment_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        payment_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        payment_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        payment_col_doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        payment_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        payment_tableView.setItems(patientListData);
    }

    public void paymentSelectItems() {

        PatientsData pData = payment_tableView.getSelectionModel().getSelectedItem();
        int num = payment_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        if (pData.getImage() != null) {
            String path = "File:" + pData.getImage();
            image = new Image(path, 145, 104, false, true);
            payment_circle.setFill(new ImagePattern(image));

            Data.temp_path = pData.getImage();

        }

        Data.temp_patientID = pData.getPatientID();
        Data.temp_name = pData.getFullName();
        Data.temp_date = String.valueOf(pData.getDate());

        payment_patientID.setText(String.valueOf(pData.getPatientID()));
        payment_name.setText(pData.getFullName());
        payment_doctor.setText(pData.getDoctor());
        payment_date.setText(String.valueOf(pData.getDate()));

    }

    public void paymentCheckOutBtn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CheckOutPatient.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Hospital Management System | Check-Out");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileUpdateBtn() {
        connect = Database.connectDB();

        if (profile_adminID.getText().isEmpty()
                || profile_fullName.getText().isEmpty()
                || profile_username.getText().isEmpty()
                || profile_email.getText().isEmpty()
                || profile_gender.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blank fields!");
        } else {
            if (Data.path == null || "".equals(Data.path)) {
                String updateData = "UPDATE admin SET username = ?, full_name = ? ,email = ?, gender = ?" +
                        "WHERE admin_id='" + Data.admin_id + "'";

                try {
                    prepare = connect.prepareStatement(updateData);

                    prepare.setString(1, profile_username.getText());
                    prepare.setString(2, profile_fullName.getText());
                    prepare.setString(3, profile_email.getText());
                    prepare.setString(4, profile_gender.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();

                    profileDisplayInfo();

                    alert.successMessage("Updated Successfully");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String updateData = "UPDATE admin SET  username = ?,full_name = ?, email = ?, image = ?, gender = ? " +
                        "WHERE admin_id='" + Data.admin_id + "'";

                try {
                    prepare = connect.prepareStatement(updateData);

                    prepare.setString(1, profile_username.getText());
                    prepare.setString(2, profile_fullName.getText());
                    prepare.setString(3, profile_email.getText());

                    String path = Data.path;
                    path = path.replace("\\", "\\\\");
                    Path transfer = Paths.get(path);

                    Path copy = Paths.get("C:\\Users\\EMMANUEL-YEGON\\admin_directory\\" + Data.admin_id + ".jpg");

                    Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                    prepare.setString(4, copy.toAbsolutePath().toString());
                    prepare.setString(5, profile_gender.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();
                    profileDisplayInfo();
                    profileDisplayImages();

                    alert.successMessage("Updated Successfully!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void profileDisplayImages() {

        String selectData = "SELECT * FROM admin WHERE admin_id='" + Data.admin_id + "'";

        connect = Database.connectDB();

        String tempPath1 = "";
        String tempPath2 = "";

        try {
            prepare = connect.prepareStatement(selectData);
            rs = prepare.executeQuery();

            if (rs.next()) {
                tempPath1 = "File:" + rs.getString("image");
                tempPath2 = "File:" + rs.getString("image");

                if (rs.getString("image") != null) {
                    image = new Image(tempPath1, 1014, 20, false, true);
                    top_profile.setFill(new ImagePattern(image));

                    image = new Image(tempPath2, 138, 95, false, true);
                    profile_circle.setFill(new ImagePattern(image));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileInsertImage() {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*jpg", "*jpeg", "*png"));

        File file = open.showOpenDialog(profile_importBtn.getScene().getWindow());

        if (file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 138, 95, false, true);
            profile_circle.setFill(new ImagePattern(image));
        }

    }

    public void profileDisplayInfo() {

        String sql = "SELECT * FROM admin WHERE admin_id='" + Data.admin_id + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                profile_adminID.setText(rs.getString("admin_id"));
                profile_username.setText(rs.getString("username"));
                profile_email.setText(rs.getString("email"));
                profile_fullName.setText(rs.getString("full_name"));
                profile_gender.getSelectionModel().select(rs.getString("gender"));

                profile_label_adminID.setText(rs.getString("admin_id"));
                profile_label_username.setText(rs.getString("full_name"));
                profile_label_email.setText(rs.getString("email"));
                profile_label_dateCreated.setText(rs.getString("date"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileGenderList() {
        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        profile_gender.setItems(listData);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            dashboardAD();
            dashboardTP();
            dashboardAP();
            dashboardTA();
            dashboardGetDoctorDisplayData();

            current_form.setText("Dashboard Form");
        } else if (event.getSource() == doctors_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            doctorActionButtons();
            doctorDisplayData();

            current_form.setText("Doctor's Form");
        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            patientDisplayData();
            patientActionButtons();

            current_form.setText("Patient's Form");
        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
            profile_form.setVisible(false);
            payment_form.setVisible(false);

            appointmentDisplayData();

            current_form.setText("Appointment's Form");
        } else if (event.getSource() == payment_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
            payment_form.setVisible(true);

            paymentDisplayData();

            current_form.setText("Payment Form");
        } else if (event.getSource() == profile_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(true);
            payment_form.setVisible(false);

            profileDisplayInfo();
            profileDisplayImages();

            current_form.setText("Profile Form");
        }

    }

    public void displayAdminIDUsername() {
        String sql = "SELECT * FROM admin WHERE username='" + Data.admin_username + "'";

        Connection connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            if (rs.next()) {
                nav_adminID.setText(rs.getString("admin_id"));
                String tempUsername = rs.getString("username");
                tempUsername = tempUsername.substring(0, 1).toUpperCase() + tempUsername.substring(1);
                nav_username.setText(tempUsername);
                top_username.setText(tempUsername);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logoutBtn() {
        try {
            if (alert.confirmationMessage("Are you sure you want to logout?")) {
                Parent root = FXMLLoader.load(getClass().getResource("Hms.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                logout_btn.getScene().getWindow().hide();
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
        runTime();
        displayAdminIDUsername();


        dashboardAD();
        dashboardTP();
        dashboardAP();
        dashboardTA();

        dashboardGetDoctorDisplayData();
        dashboardPatientDataChart();
        dashboardDoctorDataChart();


        doctorDisplayData();
        doctorActionButtons();

        patientDisplayData();
        patientActionButtons();

        appointmentDisplayData();
        appointmentActionButtons();

        paymentDisplayData();


        profileGenderList();
        profileDisplayInfo();
        profileDisplayImages();

    }
}
