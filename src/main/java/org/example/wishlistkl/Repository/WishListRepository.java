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
        // 1 : opret username med parameterne String userName, String name, String email, string phoneNumber
        // ID er automatisk generet i SQL tabellen
        // 2 : tilføj en ny bruger til SQL med prepared statements og connect()
        // 3 : kontrol af at der ikke er en duplikat af username og email
        // 4 : brug af exceptions til fejl håndtering
        // 5 : evt kontrol print af tilføjelsen
    }

    public void getUser() throws SQLException {
        // 1 : find et specific username med et SQL kriterie
        // 2 : returnere username
        // antagelse af email link til login
        // 3: kontrol tjek af login med evt println
    }


    public void addWishList(WishList wishlist) throws SQLException {
        String insertWishListSQL = "INSERT INTO wishlist (username) VALUES (?)";
        String insertItemSQL = "INSERT INTO wishlist_items (wishlistId, object) VALUES (?, ?)";

        try (Connection connection = connect();
             PreparedStatement wishlistStmt = connection.prepareStatement(insertWishListSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement itemStmt = connection.prepareStatement(insertItemSQL)) {

            // Deactivate auto-commit for transaction management
            connection.setAutoCommit(false);

            // Insert wishlist and retrieve generated wishlistId
            wishlistStmt.setString(1, wishlist.getUsername());
            wishlistStmt.executeUpdate();

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
                    connection.commit();
                    System.out.println("Wishlist added successfully for user: " + wishlist.getUsername());
                } else {
                    throw new SQLException("Failed to insert new wishlist, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction if error
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
