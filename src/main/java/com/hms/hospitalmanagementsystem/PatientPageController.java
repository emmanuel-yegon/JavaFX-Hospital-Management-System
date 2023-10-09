package com.hms.hospitalmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientPageController implements Initializable {
    @FXML
    private CheckBox login_checkBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_loginBtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_patientID;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private AnchorPane main_form;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;

    private final AlertMessage alert = new AlertMessage();

    @FXML
    void loginAccount() {

        if (login_patientID.getText().isEmpty()
                || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {

            String sql = "SELECT * FROM patient WHERE patient_id = ? AND password = ? AND date_delete IS NULL ";
            connect = Database.connectDB();

            try {

                if (!login_showPassword.isVisible()) {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_showPassword.setText(login_password.getText());
                    }
                } else {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_password.setText(login_showPassword.getText());
                    }
                }

                String checkStatus = "SELECT status FROM patient WHERE patient_id='" + login_patientID.getText() + "'  AND password ='" + login_password.getText() + "' AND status='Confirm'";

                prepare = connect.prepareStatement(checkStatus);
                rs = prepare.executeQuery();

                if (rs.next()) {

                    if (!login_showPassword.isVisible()) {
                        if (!login_showPassword.getText().equals(login_password.getText())) {
                            login_showPassword.setText(login_password.getText());
                        }
                    } else {
                        if (!login_showPassword.getText().equals(login_password.getText())) {
                            login_password.setText(login_showPassword.getText());
                        }
                    }

                    alert.errorMessage("Need the confirmation of the Admin!");
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, login_patientID.getText());
                    prepare.setString(2, login_password.getText());

                    rs = prepare.executeQuery();

                    if (rs.next()) {
                        Data.patient_id = Integer.parseInt(login_patientID.getText());

                        alert.successMessage("Login Successfully!");

                        Parent root = FXMLLoader.load(getClass().getResource("PatientMainForm.fxml"));
                        Stage stage = new Stage();

                        stage.setScene(new Scene(root));
                        stage.show();

                        login_loginBtn.getScene().getWindow().hide();

                    } else {
                        alert.errorMessage("Incorrect Patient ID/Password");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void loginShowPassword() {
        if (login_checkBox.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_password.setVisible(false);
            login_showPassword.setVisible(true);
        } else {
            login_password.setText(login_showPassword.getText());
            login_password.setVisible(true);
            login_showPassword.setVisible(false);
        }
    }


    @FXML
    void switchForm() {

    }

    public void userList() {

        List<String> listU = new ArrayList<>();

        for (String data : Users.user) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);

    }

    @FXML
    void switchPage() {
        if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("Hms.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(340);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (login_user.getSelectionModel().getSelectedItem() == "Doctor Portal") {

            try {


                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(340);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (login_user.getSelectionModel().getSelectedItem() == "Patient Portal") {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(340);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        login_user.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList();
    }
}
