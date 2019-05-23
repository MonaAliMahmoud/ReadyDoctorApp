package com.example.shaza.readydoctorapp.Model;

import java.util.Date;

/**
 * Created by shaza on 09/06/2018.
 */

public class Patient {
    private String fullName ;
    private String userName ;
    private String Patient_Password ;
    private String confirmPassword ;
    private String Patient_Email ;
    private String phoneNumber ;
    private Date dataofBirth ;

    public  Patient(String s, String toString){
    }

    public Patient(String FullName, String UserName, String Password, String ConfirmPassword, String PatientEmail, String PhoneNumber, Date DataOfBirth) {
        this.fullName = FullName;
        this.userName = UserName;
        this.Patient_Password = Password;
        this.confirmPassword = ConfirmPassword;
        this.Patient_Email = PatientEmail;
        this.phoneNumber = PhoneNumber;
        this.dataofBirth = DataOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName; }


    public String getPassword() {
        return Patient_Password;
    }

    public void setPassword(String password) {
        this.Patient_Password = password;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }


    public String getEmail() {
        return Patient_Email;
    }

    public void setEmail(String email) {
        this.Patient_Email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Date getDataofBirth() {
        return dataofBirth;
    }

    public void setDataofBirth(Date dataofBirth) {
        this.dataofBirth = dataofBirth;
    }
}
