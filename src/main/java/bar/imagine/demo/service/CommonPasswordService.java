package bar.imagine.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CommonPasswordService {
    private final Set<String> commonPasswords = new HashSet<>();

    @PostConstruct
    public void loadPasswords() {
        try (InputStream inputStream = getClass().getResourceAsStream("/common_passwords.txt")) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: /common_passwords.txt");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                reader.lines()
                    .map(String::trim)
                    .forEach(commonPasswords::add);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load common passwords", e);
        }
    }

    public boolean isCommonPassword(String passwordAsString) {
        return commonPasswords.contains(passwordAsString);
    }
}
