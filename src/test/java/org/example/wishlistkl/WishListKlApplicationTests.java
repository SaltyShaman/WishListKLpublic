package org.example.wishlistkl;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.Repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.*;
import java.util.Arrays;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    void testAddUser() throws SQLException {
        // Create a new user instance
        User user = new User("testUsername27", "TestName", "test@example.com", "123456789");

        // Print out user details to verify they are correctly initialized
        System.out.println("Testing with User: " + user);

        // Call the addUser method to insert the user into the database
        wishListRepository.addUser(user);

        // Verify that the user object now contains a generated ID
        Assertions.assertNotNull(user.getId(), "Generated ID should not be null after insertion.");

        // Verify that the user exists in the database using the repository method
        boolean userExists = wishListRepository.userExists(user.getUsername());
        Assertions.assertTrue(userExists, "User should exist for username: " + user.getUsername());

        // Print the generated ID for visibility during testing
        System.out.println("Generated User ID: " + user.getId());
        System.out.println(user);
    }



// 15, 16, 11, 13, 14 blev sprunget over grundet duplikat entry, 15, 11, 13, 14



    @Test
    void addWishList() {
        // Create a WishList object with only the username and objects
        org.example.wishlistkl.model.WishList wishlist = new org.example.wishlistkl.model.WishList("TesterIJ", Arrays.asList("item1", "item2"));

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

    @Test
    void testGetUserByUsername() throws SQLException {
        // Specify the username to fetch
        String username = "testUsername16";

        // Call the method to fetch the user data
        User user = wishListRepository.getUserByUsername(username);

        // Verify the user was found and the data matches
        Assertions.assertNotNull(user, "User should not be null.");
        Assertions.assertEquals("testUsername16", user.getUsername(), "Username should match.");
        System.out.println("Fetched User: " + user);
    }

// kan hente nyligt indsat data der IKKE dukker op i en SQL tabl på en manuel querry
// kan hente manuelt indsat manuelt i SQL
}