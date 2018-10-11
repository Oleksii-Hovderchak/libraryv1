package ua.nure.library;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
//        String originalInput = "Denys_Grynov@epam.com:sdjf";
//        Base64 base64 = new Base64();
//        String encodedString = new String(base64.encode(originalInput.getBytes()));
//        System.out.println(encodedString);
//
//        String decoded = new String(Base64.decodeBase64(encodedString.getBytes()));
//        System.out.println(decoded);

        System.out.println(bCryptPasswordEncoder.encode("DOROVA"));
        System.out.println(bCryptPasswordEncoder.matches("DOROVA", "$2a$10$PuuDTNQctzUimjD8QxVfCeB7X2bo/7LuT3EoCyo8CMzIsw0ybkKoG"));

        String originalInput = "oleksii.hovderchak@nure.ua:admin";
        System.out.println(new String(Base64.encodeBase64(originalInput.getBytes())));
    }
}
