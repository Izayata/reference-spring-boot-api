package bar.imagine.demo.service;

import bar.imagine.demo.converter.MyUserConverter;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.PasswordChangeDTO;
import bar.imagine.demo.dto.myUser.PasswordDTO;
import bar.imagine.demo.dto.NewPasswordDetailsDTO;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.repository.CustomerRepository;
import bar.imagine.demo.repository.EmailOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock private UserService userService;
    @Mock private MyUserConverter myUserConverter;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private EmailOutboxRepository emailOutboxRepository;
    @Mock private EmailService emailService;
    @Mock private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    private MyUser buildUser(String hash) {
        return MyUser.builder()
            .id(1L)
            .myUsername(new MyUsername("testuser"))
            .email(new Email("test@example.com"))
            .password(new Password(hash))
            .build();
    }

    private PasswordChangeDTO buildPasswordChangeDto(String currentPassword, String newPassword) {
        return PasswordChangeDTO.builder()
            .currentPassword(new PasswordDTO(currentPassword))
            .newPasswordDetails(NewPasswordDetailsDTO.builder()
                .newPassword(new PasswordDTO(newPassword))
                .confirmNewPassword(new PasswordDTO(newPassword))
                .build())
            .build();
    }

    @Test
    void getAuthenticatedUserProfileAsMyUserDTO_delegatesToUserServiceAndConverter() {
        MyUser user = buildUser("$2a$10$oldHash");
        Customer freshCustomer = Customer.builder().build();
        MyUserDTO dto = MyUserDTO.builder().build();
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.of(freshCustomer));
        when(myUserConverter.convertMyUserToMyUserDto(user)).thenReturn(dto);

        assertSame(dto, accountService.getAuthenticatedUserProfileAsMyUserDTO());
    }

    @Test
    void getAuthenticatedUserProfileAsMyUserDTO_setsFreshlyFetchedCustomer_beforeConverting() {
        MyUser user = buildUser("$2a$10$oldHash");
        Customer freshCustomer = Customer.builder().build();
        MyUserDTO dto = MyUserDTO.builder().build();
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.of(freshCustomer));
        when(myUserConverter.convertMyUserToMyUserDto(user)).thenReturn(dto);

        accountService.getAuthenticatedUserProfileAsMyUserDTO();

        assertSame(freshCustomer, user.getCustomer());
    }

    @Test
    void getAuthenticatedUserProfileAsMyUserDTO_throws_whenCustomerNotFound() {
        MyUser user = buildUser("$2a$10$oldHash");
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            accountService.getAuthenticatedUserProfileAsMyUserDTO());
    }

    @Test
    void updateAuthenticatedUserPassword_succeeds_whenCurrentPasswordMatches() {
        MyUser user = buildUser("$2a$10$oldHash");
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(passwordEncoder.matches(eq("OldPassword@1"), eq("$2a$10$oldHash"))).thenReturn(true);
        when(passwordEncoder.encode("NewPassword@1")).thenReturn("$2a$10$newHash");
        when(emailService.buildPasswordChangeConfirmationEmail(any())).thenReturn(new EmailContent("subject", "body"));

        accountService.updateAuthenticatedUserPassword(buildPasswordChangeDto("OldPassword@1", "NewPassword@1"));

        assertEquals("$2a$10$newHash", user.getPassword().getValue());
        verify(userService).saveMyUser(user);
        verify(emailOutboxRepository).save(any(EmailOutbox.class));
    }

    @Test
    void updateAuthenticatedUserPassword_throws_whenCurrentPasswordDoesNotMatch() {
        MyUser user = buildUser("$2a$10$oldHash");
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(passwordEncoder.matches(eq("WrongPassword@1"), eq("$2a$10$oldHash"))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
            accountService.updateAuthenticatedUserPassword(buildPasswordChangeDto("WrongPassword@1", "NewPassword@1")));

        verify(userService, never()).saveMyUser(any());
        verify(emailOutboxRepository, never()).save(any());
    }
}
