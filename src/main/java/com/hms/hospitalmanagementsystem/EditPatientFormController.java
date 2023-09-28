package com.hms.hospitalmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditPatientFormController implements Initializable {
    @FXML
    private TextArea edit_address;

    @FXML
    private ComboBox<String> edit_gender;

    @FXML
    private TextField edit_mobileNumber;

    @FXML
    private TextField edit_name;

    @FXML
    private TextField edit_patientID;

    @FXML
    private ComboBox<String> edit_status;

    @FXML
    private Button edit_updateBtn;

    @FXML
    private AnchorPane main_form;

    private AlertMessage alert = new AlertMessage();

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement statement;


    public void updateBtn() {

        if (edit_patientID.getText().isEmpty()
                || edit_name.getText().isEmpty()
                || edit_gender.getSelectionModel().getSelectedItem() == null
                || edit_mobileNumber.getText().isEmpty()
                || edit_address.getText().isEmpty()
                || edit_status.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blanl fields");
        } else {

            String updateData = "UPDATE patient SET full_name = ?,gender = ?,mobile_number = ?,address = ?," +
                    "status = ?, date_modify = ? WHERE patient_id='"+edit_patientID.getText()+"'";
            connect = Database.connectDB();

            try {

                if (alert.confirmationMessage("Are you sure you want to UPDATE Patient ID:"+ edit_patientID.getText() +"?")){

                    prepare = connect.prepareStatement(updateData);
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(1,edit_name.getText());
                    prepare.setString(2,edit_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(3,edit_mobileNumber.getText());
                    prepare.setString(4,edit_address.getText());
                    prepare.setString(5,edit_status.getSelectionModel().getSelectedItem());
                    prepare.setString(6,String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert.successMessage("Updated Successfully!");

                }else {
                    alert.errorMessage("Cancelled.");
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void setField() {

        edit_patientID.setText(String.valueOf(Data.temp_patientID));
        edit_name.setText(Data.temp_name);
        edit_gender.getSelectionModel().select(Data.temp_gender);
        edit_address.setText(Data.temp_address);
        edit_status.getSelectionModel().select(Data.temp_status);
        edit_mobileNumber.setText(String.valueOf(Data.temp_number));

    }

    public void genderList() {

        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        edit_gender.setItems(listData);

    }

    public void statusList() {

        List<String> listS = new ArrayList<>();

        for (String data : Data.status) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        edit_status.setItems(listData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setField();

        genderList();

        statusList();
    }
}
