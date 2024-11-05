package org.example.wishlistkl.model;

public class WishList {

    private String username;
    private int wishListId;
    private String object;

    public WishList(String username, int wishListId, String object) {
        this.username = username;
        this.wishListId = wishListId;
        this.object = object;
    }

    public WishList() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "username='" + username + '\'' +
                ", wishListId=" + wishListId +
                ", object='" + object + '\'' +
                '}';
    }
}
