package org.example.wishlistkl.Repository;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.model.WishList;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class WishListRepository {

    private String url = System.getenv("url");
    private String user = System.getenv("username");
    private String password = System.getenv("password");

    // Establishing the database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // overordnet rækkefølge:
    // 1: repo
    // 2: service der returnere repo metoderne
    // 3: controller klasser der returnere værdien af service metoden og evt. returnere den på en HTML side

    public void addUser(@NotNull User user) throws SQLException {
        // SQL query for inserting user data
        String query = "INSERT INTO user(username, name, email, phoneNumber) VALUES (?, ?, ?, ?)";

        // Initialize the connection
        Connection conn = connect();

        try (
                // Prepare the statement with generated keys enabled
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Set parameters for the prepared statement
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());

            // Execute the insert and retrieve generated keys
            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // Assumes ID is the first column
                    user.setId(id); // Set the generated ID in the User object
                    System.out.println("Generated User ID: " + id);
                } else {
                    throw new SQLException("Failed to retrieve generated ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection in the finally block to ensure it gets closed
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public boolean userExists(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ? LIMIT 1";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()){
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

        }

    }


    public User getUser(String username) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phonenumber");

                    user = new User(username, name, email, phoneNumber); // Assuming the ID is not needed directly here
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void addWishList(@NotNull WishList wishlist) throws SQLException {
        String insertWishListSQL = "INSERT INTO wishlist (username) VALUES (?)";
        String insertItemSQL = "INSERT INTO wishlist_items (wishlistId, object) VALUES (?, ?)";

        Connection conn = connect();

        // Declare the statements outside the try block
        PreparedStatement wishlistStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet generatedKeys = null;

        try {
            // Prepare the SQL statements
            wishlistStmt = conn.prepareStatement(insertWishListSQL, Statement.RETURN_GENERATED_KEYS);
            itemStmt = conn.prepareStatement(insertItemSQL);

            // Deactivate auto-commit for transaction management
            conn.setAutoCommit(false);

            // Insert wishlist and retrieve generated wishlistId
            wishlistStmt.setString(1, wishlist.getUsername());
            wishlistStmt.executeUpdate();

            // Retrieve the generated wishlistId
            generatedKeys = wishlistStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int wishlistId = generatedKeys.getInt(1); // Retrieve generated wishlistId

                // Loop through objects and insert each into wishlist_items
                for (String object : wishlist.getObjects()) {
                    itemStmt.setInt(1, wishlistId);
                    itemStmt.setString(2, object);
                    itemStmt.executeUpdate();
                }

                // Commit transaction if everything is successful
                conn.commit();
                System.out.println("Wishlist added successfully for user: " + wishlist.getUsername());
            } else {
                throw new SQLException("Failed to insert new wishlist, no ID obtained.");
            }
        } catch (SQLException e) {
            // Rollback transaction if any error occurs
            if (conn != null) {
                conn.rollback();
            }
            System.err.println("Transaction rolled back due to error: " + e.getMessage());
            throw e;
        } finally {
            // Close the ResultSet and PreparedStatements to avoid resource leaks
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (wishlistStmt != null) {
                try {
                    wishlistStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (itemStmt != null) {
                try {
                    itemStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public boolean wishlistExists(String username)throws SQLException {
        String query = "SELECT 1 FROM wishlist WHERE username = ? LIMIT 1";  // check if username has a wishlist

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            //username is the first parameter
            stmt.setString(1, username);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                // If result is found, return true
                return rs.next();  // If result set is not empty, wishlist exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Re-throw the exception after logging it
        }
    }
}
