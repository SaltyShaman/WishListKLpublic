package org.example.wishlistkl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WishListKlApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testenv(){

        String username = System.getenv("username");
        String password = System.getenv("password");
        String url = System.getenv("url");

        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("url: " + url);

    }
}
