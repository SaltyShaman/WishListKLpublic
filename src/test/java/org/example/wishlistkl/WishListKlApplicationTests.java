package org.example.wishlistkl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WishListKlApplicationTests {

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
}
