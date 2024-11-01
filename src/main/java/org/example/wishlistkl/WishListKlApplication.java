package org.example.wishlistkl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WishListKlApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishListKlApplication.class, args);

        String username = System.getenv("username");
        String password = System.getenv("password");
        String url = System.getenv("url");

        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("url: " + url);

    }

}
