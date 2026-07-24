package bar.imagine.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonPasswordServiceTest {

    private CommonPasswordService commonPasswordService;

    @BeforeEach
    void setUp() {
        commonPasswordService = new CommonPasswordService();
        commonPasswordService.loadPasswords();
    }

    @Test
    void isCommonPassword_returnsTrue_forKnownCommonPassword() {
        assertTrue(commonPasswordService.isCommonPassword("password"));
    }

    @Test
    void isCommonPassword_returnsFalse_forUncommonPassword() {
        assertFalse(commonPasswordService.isCommonPassword("Xk9#mQ7$vLp2!wZs"));
    }

    @Test
    void loadPasswords_worksFromClasspathResourceStream_notFileSystemPath() {
        // Regression guard for the Tier 24 fat-JAR fix: loadPasswords must succeed using
        // getClass().getResourceAsStream(...), which works both from exploded classes and from
        // inside a packaged JAR, unlike the old Files.readAllLines(Path.of(url.toURI())) approach.
        assertTrue(commonPasswordService.isCommonPassword("123456"));
    }
}
