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

    // Display the form for creating a new user
    @GetMapping("/create-user")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    // Handle form submission for creating a new user
    @PostMapping("/create-user")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        try {
            if (wishListService.checkIfUserExists(user.getUsername())) {
                model.addAttribute("message", "User already exists: " + user.getUsername());
                return "create-user"; // Return to form with error message
            }
            wishListService.addUser(user);
            model.addAttribute("message", "User created successfully: " + user.getUsername());
            return "create-user"; // Return to form with success message
        } catch (Exception e) {
            model.addAttribute("message", "Error while creating user: " + e.getMessage());
            return "create-user"; // Return to form with error message
        }
    }

    @GetMapping("/create-wishlist")
    public String showCreateWishlistForm(Model model) {
        model.addAttribute("wishlist", new WishList());
        return "create-wishlist";
    }

    // Handle form submission for creating a new wishlist
    @PostMapping("/create-wishlist")
    public String createWishList(@ModelAttribute("wishlist") WishList wishList, Model model) {
        try {
            if (wishListService.checkIfWishlistExists(wishList.getUsername())) {
                model.addAttribute("message", "Wishlist already exists for user: " + wishList.getUsername());
                return "create-wishlist"; // Return to form with error message
            }
            wishListService.addWishList(wishList);
            model.addAttribute("message", "Wishlist created successfully for user: " + wishList.getUsername());
            return "create-wishlist"; // Return to form with success message
        } catch (Exception e) {
            model.addAttribute("message", "Error while creating wishlist: " + e.getMessage());
            return "create-wishlist"; // Return to form with error message
        }
    }
}
