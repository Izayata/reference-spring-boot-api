package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.ImageURLUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class ImageURLDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final ImageURLDTO ERR_IMAGE_URL_DTO_EMPTY = new ImageURLDTO("");
    private static final ImageURLDTO ERR_IMAGE_URL_DTO_NULL = new ImageURLDTO(null);
    private static final ImageURLDTO ERR_IMAGE_URL_DTO_SPACE_ONLY = new ImageURLDTO("   ");
    private static final String VALID_URL_PREFIX = "https://example.com/";
    private static final ImageURLDTO ERR_IMAGE_URL_DTO_TOO_LONG = new ImageURLDTO(
        VALID_URL_PREFIX + "a".repeat(IMAGE_URL_VALUE_MAX_LENGTH + 1 - VALID_URL_PREFIX.length()));
    private static final ImageURLDTO ERR_IMAGE_URL_DTO_INVALID_FORMAT = new ImageURLDTO("not-a-url");
    public static final ImageURLDTO VALID_IMAGE_URL_DTO = new ImageURLDTO("https://example.com/image.jpg");
    private static final ImageURLDTO VALID_IMAGE_URL_DTO_MAX_LENGTH = new ImageURLDTO(
        VALID_URL_PREFIX + "a".repeat(IMAGE_URL_VALUE_MAX_LENGTH - VALID_URL_PREFIX.length()));

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testImageUrlDtoValidSimple() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(VALID_IMAGE_URL_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testImageUrlDtoValidMaxLength() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(VALID_IMAGE_URL_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testImageUrlDtoInvalidEmpty() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(ERR_IMAGE_URL_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_VALUE_REQUIRED)));
        }

        @Test
        void testImageUrlDtoInvalidNull() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(ERR_IMAGE_URL_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_VALUE_REQUIRED)));
        }

        @Test
        void testImageUrlDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(ERR_IMAGE_URL_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_VALUE_REQUIRED)));
        }

        @Test
        void testImageUrlDtoInvalidTooLong() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(ERR_IMAGE_URL_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_VALUE_LENGTH)));
        }

        @Test
        void testImageUrlDtoInvalidFormat() {
            Set<ConstraintViolation<ImageURLDTO>> violations = validator.validate(ERR_IMAGE_URL_DTO_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT)));
        }
    }
}
