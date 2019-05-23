package com.example.shaza.readydoctorapp.Model;

/**
 * Created by shaza on 09/06/2018.
 */

public class Doctor {

    public String name ;
    public String password ;
    public float rate ;
    public float fees ;
    public double longitude ;
    public double latitude ;
    public String Specialization;
    public String phone;

    public Doctor(){
    }

    public Doctor(String name, String password , float rate , float fees , double longitude , double latitude, String Specialization, String phone) {
        this.name = name;
        this.password = password;
        this.rate = rate ;
        this.fees = fees ;
        this.longitude = longitude ;
        this.latitude = latitude ;
        this.Specialization = Specialization;
        this.phone = phone;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }


    public float getRate() { return rate; }

    public void setRate(float rate) { this.rate = rate; }


    public float getFees() { return fees; }

    public void setFees(float fees) { this.fees = fees; }


    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }


    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }


    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }


    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
