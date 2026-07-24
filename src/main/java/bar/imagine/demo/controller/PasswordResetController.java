package bar.imagine.demo.controller;

import bar.imagine.demo.dto.TokenValidationResult;
import bar.imagine.demo.request.data.ResetPasswordRequestData;
import bar.imagine.demo.request.data.ForgottenPasswordRequestData;
import bar.imagine.demo.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @PostMapping("/request-password-reset-link")
    public ResponseEntity<String> requestPasswordReset(
        @Valid
        @RequestBody
        ForgottenPasswordRequestData requestData
    ) {
        return ResponseEntity.ok(passwordResetService.requestPasswordReset(requestData));
    }

    // Token is intentionally a query parameter (not a request body) so the frontend can validate it
    // via a plain GET when the user opens the emailed reset link. Trade-off: query params can be
    // recorded in access logs and browser history. Mitigated by the token being single-use,
    // short-lived (see app.password-reset.token-expiry-minutes), and rate-limited per email.
    @GetMapping("/validate")
    public TokenValidationResult validateResetToken(@RequestParam String token) {
        return passwordResetService.validateResetToken(token);
    }

    @PostMapping("/set-new-password")
    public ResponseEntity<String> resetPassword(
        @Valid
        @RequestBody
        ResetPasswordRequestData requestData
    ) {
        return ResponseEntity.ok(passwordResetService.setNewPassword(requestData));
    }
}
