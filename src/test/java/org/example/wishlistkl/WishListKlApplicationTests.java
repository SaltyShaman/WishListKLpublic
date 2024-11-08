package org.example.wishlistkl;

import org.example.wishlistkl.Model.User;
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

    @Test
    void testAddUser() throws SQLException {
        // Create a new user instance
        User user = new User("testUsername", "Test Name", "test@example.com", "123456789");

        // Call the addUser method to insert the user into the database
        wishListRepository.addUser(user);

        // Check if the user was added by querying the database
        try (Connection conn = DriverManager.getConnection(System.getenv("url"),
                System.getenv("username"),
                System.getenv("password"));
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {

            stmt.setString(1, user.getUser());
            ResultSet resultSet = stmt.executeQuery();

            // Assert that the user was added to the database
            assertTrue(resultSet.next(), "User should be present in the database.");
            assertNotNull(resultSet.getInt("id"), "Generated ID should not be null.");
            System.out.println("Inserted User ID: " + resultSet.getInt("id"));
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
