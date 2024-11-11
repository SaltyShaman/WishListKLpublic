package org.example.wishlistkl.Controller;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.Service.WishListService;
import org.example.wishlistkl.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlists")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            if (wishListService.checkIfUserExists(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists: " + user.getUsername());
            }
            wishListService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully: " + user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating user: " + e.getMessage());
        }
    }

    // API for creating a new Wishlist (returns ResponseEntity)
    @PostMapping("/wishlists")
    public ResponseEntity<String> createWishList(@RequestBody WishList wishList) {
        try {
            if (wishListService.checkIfWishlistExists(wishList.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Wishlist already exists for user: " + wishList.getUsername());
            }
            wishListService.addWishList(wishList);
            return ResponseEntity.status(HttpStatus.CREATED).body("Wishlist created successfully for user: " + wishList.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating wishlist: " + e.getMessage());
        }
    }

    // Thymeleaf form handler for creating a new Wishlist (returns a view)
    @PostMapping("/wishlists/form")
    public String createWishList(@ModelAttribute("wishlist") WishList wishList, Model model) {
        try {
            if (wishListService.checkIfWishlistExists(wishList.getUsername())) {
                model.addAttribute("message", "Wishlist already exists for user: " + wishList.getUsername());
                return "create-wishlist"; // Return to the same form with error message
            }

            // Add the wishlist using the service
            wishListService.addWishList(wishList);
            model.addAttribute("message", "Wishlist created successfully for user: " + wishList.getUsername());
            return "create-wishlist"; // Redirect to the same page or a success page
        } catch (Exception e) {
            model.addAttribute("message", "Error while creating wishlist: " + e.getMessage());
            return "create-wishlist"; // Return to the form with error message
        }
    }
}
