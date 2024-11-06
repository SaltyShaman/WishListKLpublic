package org.example.wishlistkl.Service;

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

}
