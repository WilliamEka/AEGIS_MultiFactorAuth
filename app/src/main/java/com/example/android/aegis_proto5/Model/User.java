package com.example.android.aegis_proto5.Model;

public class User {

    private String username;
    private String email;
    private String password;
    private String phone;
    private String imei;

    public User() {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.imei = imei;
    }

    public User(String email, String username, String password, String phone, String imei){
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.imei = imei;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
