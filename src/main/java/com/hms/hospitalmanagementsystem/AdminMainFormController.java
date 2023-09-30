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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminMainFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> appointments_action;

    @FXML
    private TableColumn<?, ?> appointments_appointmentID;

    @FXML
    private Button appointments_btn;

    @FXML
    private TableColumn<?, ?> appointments_contact;

    @FXML
    private TableColumn<?, ?> appointments_date;

    @FXML
    private TableColumn<?, ?> appointments_dateDelete;

    @FXML
    private TableColumn<?, ?> appointments_dateModify;

    @FXML
    private TableColumn<?, ?> appointments_description;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private TableColumn<?, ?> appointments_gender;

    @FXML
    private TableColumn<?, ?> appointments_name;

    @FXML
    private TableColumn<?, ?> appointments_status;

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
    private TableColumn<PatientsData,String> patients_col_action;

    @FXML
    private TableColumn<PatientsData,String> patients_col_contactNumber;

    @FXML
    private TableColumn<PatientsData,String> patients_col_date;

    @FXML
    private TableColumn<PatientsData,String> patients_col_dateDelete;

    @FXML
    private TableColumn<PatientsData,String> patients_col_dateModify;

    @FXML
    private TableColumn<PatientsData,String> patients_col_description;

    @FXML
    private TableColumn<PatientsData,String> patients_col_gender;

    @FXML
    private TableColumn<PatientsData,String> patients_col_name;

    @FXML
    private TableColumn<PatientsData,String> patients_col_patientID;

    @FXML
    private TableColumn<PatientsData,String> patients_col_status;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private TableView<PatientsData> patients_tableView;

    @FXML
    private Button payment_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet rs;

    private AlertMessage alert = new AlertMessage();

    public ObservableList<DoctorData> doctorGetData() {

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM doctor WHERE date_delete IS NULL";

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

    private ObservableList<DoctorData> doctorListData;

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

    public ObservableList<PatientsData> patientGetData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM patient WHERE date_delete IS NULL";

        try {
            prepare = connect.prepareStatement(sql);
            rs = prepare.executeQuery();

            PatientsData pData;

            while (rs.next()) {
                pData = new PatientsData(rs.getInt("id"), rs.getInt("patient_id")
                        , rs.getString("password"), rs.getString("full_name")
                        , rs.getLong("mobile_number"), rs.getString("address")
                        , rs.getString("image"), rs.getString("description")
                        , rs.getString("diagnosis"), rs.getString("treatment")
                        , rs.getString("doctor"), rs.getString("specialized")
                        , rs.getDate("date"), rs.getDate("date_modify")
                        ,rs.getDate("date_delete"), rs.getString("status"));

                listData.add(pData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<PatientsData> patientListData;

    public void patientDisplayData(){
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

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);


        } else if (event.getSource() == doctors_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);

            doctorActionButtons();
            doctorDisplayData();

        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);

            patientDisplayData();

        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
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

        doctorDisplayData();
        doctorActionButtons();

        patientDisplayData();
    }
}
