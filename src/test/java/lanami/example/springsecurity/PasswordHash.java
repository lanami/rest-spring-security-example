package lanami.example.springsecurity;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-05
 */

public class PasswordHash {

    /**
     * Utility method to generate password hashes.
     * */
    @Test
    public void generateMD5Hash() {
        String password = "user";
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String hashedPassword = encoder.encodePassword(password, null);
        System.out.printf("MD5 hash for \'%s\': %s\n", password, hashedPassword);
    }

    /**
     * Utility method to generate password hashes.
     * */
    @Test
    public void generateBCryptHash() {
        String password = "admin";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        System.out.printf("BCrypt hash for \'%s\': %s\n", password, hashedPassword);
    }
}
