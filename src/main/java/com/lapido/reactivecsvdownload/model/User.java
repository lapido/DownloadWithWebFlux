package com.lapido.reactivecsvdownload.model;

/**
 * created by victor abidoye on 6/12/2020
 **/
public class User {
    private String name;
    private String email;
    private String countryCode;

    public User(){

    }

    public User(String name, String email, String countryCode) {
        this.name = name;
        this.email = email;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
