package bar.imagine.demo.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.RegistrationDTO;
import jakarta.validation.Valid;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.exception.exceptions.RateLimitExceededException;
import bar.imagine.demo.request.data.CommonPasswordRequestData;
import bar.imagine.demo.service.CommonPasswordService;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CommonPasswordService commonPasswordService;
    private final RedisService redisService;

    private static final String USERNAME_EXISTS_RATE_LIMIT_KEY_PREFIX = "username_exists_rate_limit:";
    private static final String EMAIL_EXISTS_RATE_LIMIT_KEY_PREFIX = "email_exists_rate_limit:";
    private static final int MAX_EXISTENCE_CHECKS_PER_HOUR = 20;

    @GetMapping("/username/{username}/exists")
    public ResponseEntity<Map<String, Boolean>> isUsernameExist(@PathVariable String username) {
        enforceExistenceCheckRateLimit(USERNAME_EXISTS_RATE_LIMIT_KEY_PREFIX + username);
        boolean exists = registrationService.existsByUsername(new MyUsername(username));
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Map<String, Boolean>> isEmailExist(@PathVariable @jakarta.validation.constraints.Email String email) {
        enforceExistenceCheckRateLimit(EMAIL_EXISTS_RATE_LIMIT_KEY_PREFIX + email);
        boolean exists = registrationService.existsByEmail(new Email(email));
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    // Enumeration guard: caps checks per queried username/email value, mirroring the Redis
    // rate-limit pattern used for password-reset requests in PasswordResetService.
    private void enforceExistenceCheckRateLimit(String key) {
        long attempts = redisService.atomicIncrementWithTtlOnFirstWrite(key, Duration.ofHours(1));
        if (attempts > MAX_EXISTENCE_CHECKS_PER_HOUR) {
            throw new RateLimitExceededException("Too many requests. Please try again later.");
        }
    }

    @PostMapping("/common-password")
    public ResponseEntity<Map<String, Boolean>> isCommonPassword(@Valid @RequestBody CommonPasswordRequestData requestData) {
        boolean isCommon = commonPasswordService.isCommonPassword(requestData.getPassword());
        Map<String, Boolean> response = new HashMap<>();
        response.put("isCommonPassword", isCommon);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MyUserDTO> registerUserAndItsCustomer(@Valid @RequestBody RegistrationDTO registrationDto) {
        MyUserDTO createdUserDto = registrationService.register(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }
}
