package bar.imagine.demo.controller;

import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.PasswordChangeDTO;
import bar.imagine.demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public MyUserDTO getAuthenticatedUser() {
        return accountService.getAuthenticatedUserProfileAsMyUserDTO();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDto) {
        accountService.updateAuthenticatedUserPassword(passwordChangeDto);
        return ResponseEntity.noContent().build();
    }
}
