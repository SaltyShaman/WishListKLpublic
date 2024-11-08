package org.example.wishlistkl.model;

public class WishListReservations {

    private String username;
    private String email;
    private String phoneNumber;
    private String reservedObject;

    public WishListReservations(String username, String email, String phoneNumber, String reservedObject) {
        this.username = username;

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reservedObject = reservedObject;
    }

    public WishListReservations() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReservedObject() {
        return reservedObject;
    }

    public void setReservedObject(String reservedObject) {
        this.reservedObject = reservedObject;
    }

    @Override
    public String toString() {
        return "WishListReservations{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", reservedObject='" + reservedObject + '\'' +
                '}';
    }
}
