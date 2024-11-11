package org.example.wishlistkl;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.Repository.WishListRepository;
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
        User user = new User("testUsername4", "Test Name", "test@example.com", "123456789");

        // Call the addUser method to insert the user into the database
        wishListRepository.addUser(user);

        // Verify that the user object now contains a generated ID
        assertNotNull(user.getId(), "Generated ID should not be null after insertion.");

        // Verify that the user exists in the database
        boolean userExists = wishListRepository.userExists(user.getUsername());
        assertTrue(userExists, "User should exist for username: " + user.getUsername());

        // Retrieve and confirm that the user was added to the database
        try (Connection conn = DriverManager.getConnection(
                System.getenv("url"), System.getenv("username"), System.getenv("password"));
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {

            stmt.setString(1, user.getUsername());
            ResultSet resultSet = stmt.executeQuery();

            // Assert that the user was added to the database and has a generated ID
            assertTrue(resultSet.next(), "User should be present in the database.");
            int dbId = resultSet.getInt("id");
            assertEquals(user.getId(), dbId, "The ID in the User object should match the ID in the database.");
            System.out.println("Inserted User ID: " + dbId);
        }
    }
// 15, 16



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




}