package org.example.wishlistkl.Service;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository) {

        this.wishListRepository = wishListRepository;
    }

    public void addWishList(org.example.wishlistkl.model.WishList wishlist) {
        try {
            // Add wishlist to database through repository
            wishListRepository.addWishList(wishlist);
            System.out.println("WishList added successfully");
        } catch (SQLException e) {
            System.err.println("Error while adding wishlist: " + e.getMessage());
            // You can add more custom error handling here
        }
    }

    public boolean checkIfWishlistExists(String username) {
        try {
            // Call wishlistExists method from the repository
            return wishListRepository.wishlistExists(username);
        } catch (SQLException e) {
            System.err.println("Error checking if wishlist exists: " + e.getMessage());
            return false;
        }
    }

    public void addUser(User user) {
        try {
            wishListRepository.addUser(user);
            System.out.println("User added successfully");
        } catch (SQLException e) {
            System.err.println("Error while adding user: " + e.getMessage());
        }
    }

    public boolean checkIfUserExists(String username) {
        try {
            return wishListRepository.userExists(username);
        } catch (SQLException e) {
            System.err.println("Error checking if user exists: " + e.getMessage());
            return false;
        }
    }

    public void getUser(String username) {
        try {
            // Passing only the username string to the repository method
            User user = wishListRepository.getUser(username);
            if (user != null) {
                System.out.println("User found: " + user.getUser());
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error while getting user: " + e.getMessage());
        }
    }

}
