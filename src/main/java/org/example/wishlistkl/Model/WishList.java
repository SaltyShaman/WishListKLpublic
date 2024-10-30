package org.example.wishlistkl.Model;

public class WishList {
    private String user;
    private int id;
    private String name;
    private String email;
    private String phoneNumber;


    public WishList(String user, int id, String name, String email, String phoneNumber) {
        this.user = user;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    //Evt. Override toString() method.
}
