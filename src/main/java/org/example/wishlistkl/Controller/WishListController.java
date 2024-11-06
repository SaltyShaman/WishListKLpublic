package org.example.wishlistkl.Controller;


import org.example.wishlistkl.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlists")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping
    public ResponseEntity<String> addWishList(@RequestBody org.example.wishlistkl.model.WishList wishList) {
        try {
            wishListService.addWishList(wishList);
            return ResponseEntity.status(HttpStatus.CREATED).body("Wishlist added successfully for user: " + wishList.getUsername());
        } catch (Exception e) {
            e.printStackTrace();  // incase of error a print on error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while adding wishlist: " + e.getMessage());
        }
    }
}
