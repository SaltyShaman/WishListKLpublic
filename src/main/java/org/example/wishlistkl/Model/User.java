package org.example.wishlistkl.Model;

public class User {
    private String username;

    private String name;
    private String email;
    private String phoneNumber;


    public User(String username, String name, String email, String phoneNumber) {
        this.username = username;

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(){
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    }


