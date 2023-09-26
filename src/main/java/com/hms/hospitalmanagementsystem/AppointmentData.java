package com.hms.hospitalmanagementsystem;

import java.sql.Date;

public class AppointmentData {

    private Integer appointmentID;
    private Integer patientID;
    private String name;
    private String gender;
    private String description;
    private String diagnosis;
    private String treatment;
    private Long mobileNumber;
    private String address;
    private Date date;
    private Date dateModify;
    private Date dateDelete;
    private String status;
    private String doctorID;
    private String specialized;
    private Date schedule;

    public AppointmentData(Integer appointmentID, String name, String gender, String description, String diagnosis, String treatment, Long mobileNumber, Date date, Date dateModify, Date dateDelete, String status,String address,Date schedule) {
        this.appointmentID = appointmentID;
        this.name = name;
        this.gender = gender;
        this.description = description;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.mobileNumber = mobileNumber;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
        this.address = address;
        this.schedule = schedule;
    }

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public Long getMobileNumber() {
        return mobileNumber;
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

    public String getAddress() {
        return address;
    }

    public Date getSchedule() {
        return schedule;
    }
}
