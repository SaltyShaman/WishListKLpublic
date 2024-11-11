package org.example.wishlistkl.Repository;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.model.WishList;
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

    public void addUser(User user) throws SQLException {
        // 1 : oprettelse af username med parameterne String username, String name, String email, String phoneNumber
        String query = "INSERT INTO user(username, name, email, phoneNumber) VALUES (?, ?, ?, ?)";
        // ID er automatisk generet i SQL tabellen

        Connection conn = connect();

        try  {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
// se på hvordan man sender en user over med user, name, email og phoneNumber
            // 2 : tilføjer en ny bruger til SQL med prepared statements og connect()
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 3 : kontrol af at der ikke er en duplikat af username og email
    } // <-- Add closing brace here



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


    public void addWishList(WishList wishlist) throws SQLException {
        String insertWishListSQL = "INSERT INTO wishlist (username) VALUES (?)";
        String insertItemSQL = "INSERT INTO wishlist_items (wishlistId, object) VALUES (?, ?)";

        Connection conn = connect();

        try {
             PreparedStatement wishlistStmt = conn.prepareStatement(insertWishListSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)){

                // Deactivate auto-commit for transaction management
                conn.setAutoCommit(false);

                // Insert wishlist and retrieve generated wishlistId
                wishlistStmt.setString(1, wishlist.getUsername());
                wishlistStmt.executeUpdate();
            }
            try (ResultSet generatedKeys = wishlistStmt.getGeneratedKeys()) {
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
                conn.rollback(); // Rollback transaction if error
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
