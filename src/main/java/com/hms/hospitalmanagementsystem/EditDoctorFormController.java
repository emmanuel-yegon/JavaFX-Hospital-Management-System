package com.hms.hospitalmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditDoctorFormController implements Initializable {

    @FXML
    private TextArea editDoctor_address;

    @FXML
    private Button editDoctor_cancelBtn;

    @FXML
    private TextField editDoctor_doctorID;

    @FXML
    private TextField editDoctor_email;

    @FXML
    private TextField editDoctor_fullName;

    @FXML
    private ComboBox<String> editDoctor_gender;

    @FXML
    private ImageView editDoctor_imageView;

    @FXML
    private Button editDoctor_importBtn;

    @FXML
    private TextField editDoctor_mobileNumber;

    @FXML
    private TextField editDoctor_password;

    @FXML
    private ComboBox<String> editDoctor_specialized;

    @FXML
    private ComboBox<String> editDoctor_status;

    @FXML
    private Button editDoctor_updateBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement statement;

    private AlertMessage alert = new AlertMessage();

    private Image image;

    public void importBtn() {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*jpg", "*png", "*jpeg"));

        File file = open.showOpenDialog(editDoctor_importBtn.getScene().getWindow());

        if (file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 115, 125, false, true);
            editDoctor_imageView.setImage(image);

        }

    }

    public void displayDoctorData(){

        String sql ="SELECT * FROM doctor WHERE doctor_id='"
                +editDoctor_doctorID.getText()+"'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            rs=prepare.executeQuery();

            if (rs.next()){

                editDoctor_fullName.setText(rs.getString("full_name"));
                editDoctor_email.setText(rs.getString("email"));
                editDoctor_password.setText(rs.getString("password"));
                editDoctor_specialized.getSelectionModel().select(rs.getString("specialized"));
                editDoctor_gender.getSelectionModel().select(rs.getString("gender"));
                editDoctor_mobileNumber.setText(rs.getString("mobile_number"));
                editDoctor_address.setText(rs.getString("address"));
                editDoctor_status.getSelectionModel().select(rs.getString("status"));

                image = new Image("File:"+ rs.getString("image"),115,125,false,true);
                editDoctor_imageView.setImage(image);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateBtn() {
        connect = Database.connectDB();

        if (editDoctor_doctorID.getText().isEmpty()
                || editDoctor_fullName.getText().isEmpty()
                || editDoctor_email.getText().isEmpty()
                || editDoctor_password.getText().isEmpty()
                || editDoctor_specialized.getSelectionModel().getSelectedItem() == null
                || editDoctor_gender.getSelectionModel().getSelectedItem() == null
                || editDoctor_mobileNumber.getText().isEmpty()
                || editDoctor_address.getText().isEmpty()
                || editDoctor_status.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blank fields!");
        } else {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            if (Data.path == null || "".equals(Data.path)) {
                String updateData = "UPDATE doctor SET full_name='"
                        + editDoctor_fullName.getText() + "', email='"
                        + editDoctor_email.getText() + "',password='"
                        + editDoctor_password.getText() + "',specialized='"
                        + editDoctor_specialized.getSelectionModel().getSelectedItem() + "',gender='"
                        + editDoctor_gender.getSelectionModel().getSelectedItem() + "',mobile_number='"
                        + editDoctor_mobileNumber.getText() + "',address='"
                        + editDoctor_address.getText() + "', status='"
                        + editDoctor_status.getSelectionModel().getSelectedItem() + "', date_modify='"
                        + String.valueOf(sqlDate) + "' WHERE doctor_id='" + editDoctor_doctorID.getText() + "'";
                try {
                    if (alert.confirmationMessage("Are you sure you want to Update Doctor ID: " + editDoctor_doctorID.getText() + "?")) {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert.successMessage("Updated Successfully!");
                    } else {
                        alert.errorMessage("Cancelled!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displayDoctorData();
            } else {

                try {
                    if (alert.confirmationMessage("Are you sure you want to Update Doctor ID: "
                            +editDoctor_doctorID.getText() + "?")) {

                        String path = Data.path;
                        path = path.replace("\\", "\\\\");
                        Path transfer = Paths.get(path);

                        Path copy = Paths.get("C:\\Users\\EMMANUEL-YEGON\\doctor-directory\\" + editDoctor_doctorID.getText() + ".jpg");

                        try{
                            Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        String insertImage = copy.toString();
                        insertImage = insertImage.replace("\\","\\\\");

                        String updateData = "UPDATE doctor SET full_name='"
                                + editDoctor_fullName.getText() + "', email='"
                                + editDoctor_email.getText() + "',password='"
                                + editDoctor_password.getText() + "',specialized='"
                                + editDoctor_specialized.getSelectionModel().getSelectedItem() + "',gender='"
                                + editDoctor_gender.getSelectionModel().getSelectedItem() + "',mobile_number='"
                                + editDoctor_mobileNumber.getText() + "',image='"
                                + insertImage+ "',address='" + editDoctor_address.getText() + "', status='"
                                + editDoctor_status.getSelectionModel().getSelectedItem() + "'" +
                                "WHERE doctor_id='" + editDoctor_doctorID.getText() + "'";

                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert.successMessage("Updated Successfully!");
                        displayDoctorData();


                    } else {
                        alert.errorMessage("Cancelled!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void cancelBtn() {
        displayDoctorData();
    }

    public void setField() {

        editDoctor_doctorID.setText(Data.temp_doctorID);
        editDoctor_fullName.setText(Data.temp_doctorName);
        editDoctor_email.setText(Data.temp_doctorEmail);
        editDoctor_password.setText(Data.temp_doctorPassword);
        editDoctor_specialized.getSelectionModel().select(Data.temp_doctorSpecialized);
        editDoctor_gender.getSelectionModel().select(Data.temp_doctorGender);
        editDoctor_mobileNumber.setText(String.valueOf(Data.temp_doctorMobileNumber));
        editDoctor_address.setText(Data.temp_doctorAddress);
        editDoctor_status.getSelectionModel().select(Data.temp_doctorStatus);

        image = new Image("File:" + Data.temp_doctorImagePath, 115, 125, false, true);
        editDoctor_imageView.setImage(image);

    }

    public void specializationList() {

        List<String> specializationL = new ArrayList<>();

        for (String data : Data.specialization) {
            specializationL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(specializationL);
        editDoctor_specialized.setItems(listData);

    }

    public void genderList() {

        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        editDoctor_gender.setItems(listData);

    }

    public void statusList() {

        List<String> listS = new ArrayList<>();

        for (String data : Data.status) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        editDoctor_status.setItems(listData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specializationList();

        genderList();

        statusList();

        setField();
    }
}
