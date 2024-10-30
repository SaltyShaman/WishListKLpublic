package org.example.wishlistkl.Repository;


import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class WishListRepository {

    private String url = System.getenv("url");
    private String user = System.getenv("username");
    private String password = System.getenv("password");

    // Establishing the database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
