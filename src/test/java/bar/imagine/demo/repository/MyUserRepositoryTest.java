package bar.imagine.demo.repository;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class MyUserRepositoryTest {

    @Autowired
    private MyUserRepository myUserRepository;

    private static MyUser buildUser(String username, String email) {
        return MyUser.builder()
            .myUsername(new MyUsername(username))
            .password(new Password("$2a$10$WQ/oHjOGmSmkEjwEwuPpUeQKuNVB8VCVHpMhxdQnZ3fVGnOmJ3gUW"))
            .email(new Email(email))
            .build();
    }

    @Test
    void findByMyUsername_returnsUser_whenPresent() {
        MyUser saved = myUserRepository.save(buildUser("testuser", "test@example.com"));
        Optional<MyUser> found = myUserRepository.findByMyUsername(new MyUsername("testuser"));
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getMyUsername().getValue());
    }

    @Test
    void findByMyUsername_returnsEmpty_whenAbsent() {
        Optional<MyUser> found = myUserRepository.findByMyUsername(new MyUsername("nobody"));
        assertFalse(found.isPresent());
    }

    @Test
    void existsByMyUsername_returnsTrue_whenPresent() {
        myUserRepository.save(buildUser("existsuser", "exists@example.com"));
        assertTrue(myUserRepository.existsByMyUsername(new MyUsername("existsuser")));
    }

    @Test
    void existsByEmail_returnsFalse_whenAbsent() {
        assertFalse(myUserRepository.existsByEmail(new Email("nobody@example.com")));
    }

    @Test
    void findByEmail_returnsUser_whenPresent() {
        myUserRepository.save(buildUser("emailuser", "email@example.com"));
        Optional<MyUser> found = myUserRepository.findByEmail(new Email("email@example.com"));
        assertTrue(found.isPresent());
    }
}
