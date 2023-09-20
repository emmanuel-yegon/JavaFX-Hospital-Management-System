package com.hms.hospitalmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class HospitalManagementController implements Initializable {

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_loginBtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private Hyperlink login_registerHere;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private CheckBox register_checkBox;

    @FXML
    private TextField register_email;

    @FXML
    private AnchorPane register_form;

    @FXML
    private Hyperlink register_loginHere;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_showPassword;

    @FXML
    private Button register_signupBtn;

    @FXML
    private TextField register_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;

    private AlertMessage alert = new AlertMessage();

    public void loginAccount(){

        if(login_username.getText().isEmpty()
                || login_password.getText().isEmpty()){
            alert.errorMessage("Please fill all blank fields");
        }else {

            String sql = "SELECT * FROM admin WHERE username = ? AND password= ?";

            connect = Database.connectDB();

            try {

                if(!login_showPassword.isVisible()){
                    if(!login_showPassword.getText().equals(login_password.getText())){
                        login_showPassword.setText(login_password.getText());
                    }
                }else {
                    if (!login_showPassword.getText().equals(login_password.getText())){
                        login_password.setText(login_showPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1,login_username.getText());
                prepare.setString(2,login_password.getText());
                rs = prepare.executeQuery();

                if (rs.next()){
                    alert.successMessage("Login Successfully!");
                }else {
                    alert.errorMessage("Incorrect Username/Password");
                }
            }catch (Exception e){

            }
        }
    }

    public void  loginShowPassword(){

        if(login_checkBox.isSelected()){
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        }else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void registerAccount() {

        if (register_email.getText().isEmpty()
                || register_username.getText().isEmpty()
                || register_password.getText().isEmpty()) {

            alert.errorMessage("Please fill all blank fields");

        } else {

            String checkUsername = "SELECT * FROM admin WHERE username ='"
                    + register_username.getText() + "'";

            connect = Database.connectDB();

            try {

                if(!register_showPassword.isVisible()){
                    if(!register_showPassword.getText().equals(register_password.getText())){
                        register_showPassword.setText(register_password.getText());
                    }else {
                        if(!register_showPassword.getText().equals(register_password.getText())){
                            register_password.setText(register_showPassword.getText());
                        }
                    }

                }

                prepare = connect.prepareStatement(checkUsername);
                rs = prepare.executeQuery();

                if(rs.next()){

                    alert.errorMessage(register_username.getText()+" already exist!");

                } else if (register_password.getText().length() < 8) {
                    alert.errorMessage("Invalid Password, at least 8 characters needed");
                } else {

                    String insertData = "INSERT INTO admin(email,username,password,date) VALUES(?,?,?,?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1,register_email.getText());
                    prepare.setString(2,register_username.getText());
                    prepare.setString(3,register_password.getText());
                    prepare.setString(4,String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully!");
                    registerClear();

                    login_form.setVisible(true);
                    register_form.setVisible(false);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void registerClear(){

        register_email.clear();
        register_username.clear();
        register_password.clear();
        register_showPassword.clear();

    }
    public void registerShowPassword(){

        if(register_checkBox.isSelected()){
            register_showPassword.setText(register_password.getText());
            register_showPassword.setVisible(true);
            register_password.setVisible(false);
        }else {
            register_password.setText(register_showPassword.getText());
            register_showPassword.setVisible(false);
            register_password.setVisible(true);
        }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if (event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}