package com.hms.hospitalmanagementsystem;

import java.sql.Date;

public class PatientsData {

    private Integer id;
    private Integer patientID;
    private String password;
    private String fullName;
    private Long mobileNumber;
    private String address;
    private String image;
    private String description;
    private String diagnosis;
    private String treatement;
    private String doctor;
    private String specialized;
    private Date date;
    private Date dateModify;
    private Date dateDelete;
    private String status;
    private String gender;


    public PatientsData(Integer id, Integer patientID, String password, String fullName,String gender,
                        Long mobileNumber, String address, String image, String description,
                        String diagnosis, String treatement, String doctor, String specialized,
                        Date date, Date dateModify, Date dateDelete, String status) {
        this.id = id;
        this.patientID = patientID;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.image = image;
        this.description = description;
        this.diagnosis = diagnosis;
        this.treatement = treatement;
        this.doctor = doctor;
        this.specialized = specialized;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public PatientsData(Integer id, Integer patientID, String fullName,String gender, Long mobileNumber,
                        String address, Date date, Date dateModify, Date dateDelete, String status) {
        this.id = id;
        this.patientID = patientID;
        this.fullName = fullName;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatement() {
        return treatement;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getSpecialized() {
        return specialized;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }


}
