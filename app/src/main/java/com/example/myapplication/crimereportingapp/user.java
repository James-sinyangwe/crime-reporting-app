package com.example.myapplication.crimereportingapp;

public class user {
    public String firstname, lastname, NRC, PhoneNumber,Email, Password;
    public user(){

    }
    public user (String firstname,String lastname, String NRC,String PhoneNumber,String Email,String Password ){
        this.firstname = firstname;
        this.lastname =lastname;
        this.NRC =NRC;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Password =Password;

    }
}
