package org.example.wishlistkl.model;

import java.util.ArrayList;
import java.util.List;

public class WishList {

    private String username;
    private List<String> objects = new ArrayList<>();

    public WishList(String username, List<String> objects) {
        this.username = username;
        this.objects = objects;
    }

    public WishList() {
    }

    public void addObject(String object) {
        objects.add(object);
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "WishList{" +
                "username='" + username + '\'' +
                ", objects=" + objects +
                '}';
    }
}
