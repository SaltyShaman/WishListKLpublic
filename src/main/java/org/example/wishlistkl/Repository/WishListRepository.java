package org.example.wishlistkl.Repository;

import org.example.wishlistkl.Model.User;
import org.example.wishlistkl.model.WishList
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

        // 1: hent username til brug med evt login
        // 2: opret en ny ønskeliste med username og objekt(er). ID er automatisk generet
        // objekter skal måske være arrayList der læses og smides ind i SQL
        // objekt eller objekter skal eftertjekkes og eftertestes.
        // tanke om hvordan man skal skaffe objekt navne?
        // tanken er at der ikke er nogle reservede objekter på wishListen
        // kan altid lave en ny klasse der hedder addItem
        // 3: tilføj den til SQL med prepared statments med brug af connect()
        // 4: brug exception handling
        // 5: tilføj kontrol med et udprint af data tilføjet

    }
}
