package org.example.wishlistkl;

import org.example.wishlistkl.Repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@SpringBootTest
class WishListKlApplicationTests {



    @Autowired
    private WishListRepository wishListRepository;

    /*
    @BeforeEach
    void setup() {
        wishListRepository = new WishListRepository(dataSource);
    }
    */

/*
    @AfterEach
    void cleanup() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DELETE FROM wishlist_items").executeUpdate();
            connection.prepareStatement("DELETE FROM wishlist").executeUpdate();
        }
    }
*/


    @Test
    void contextLoads() {
    }



    @Test
    void testDatabaseConnection() {
        String url = System.getenv("url");
        String username = System.getenv("username");
        String password = System.getenv("password");

        if (url == null || username == null || password == null) {
            fail("Environment variables are not set correctly.");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            assertNotNull(connection);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Connection failed: " + e.getMessage());
        }
    }


/*

    @Test
    void addWishList() {
        // Create a WishList object with a placeholder wishListId (e.g., 0)
        org.example.wishlistkl.model.WishList wishlist = new org.example.wishlistkl.model.WishList("TesterIJ", 0, Arrays.asList("item1", "item2"));

        try {
            // Call the method to test
            wishListRepository.addWishList(wishlist);

            // Verify that data was added correctly
            boolean wishlistExists = wishListRepository.wishlistExists(wishlist.getUsername());
            assertTrue(wishlistExists, "Wishlist should exist for username: " + wishlist.getUsername());

            System.out.println("Wishlist and items added successfully for user: " + wishlist.getUsername());

        } catch (Exception e) {
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

*/

}
