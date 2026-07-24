package bar.imagine.demo.service;

import bar.imagine.demo.converter.MyUserConverter;
import bar.imagine.demo.converter.RegistrationConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.dto.EmailDTO;
import bar.imagine.demo.dto.MyUserRegistrationDTO;
import bar.imagine.demo.dto.NewPasswordDetailsDTO;
import bar.imagine.demo.dto.RegistrationDTO;
import bar.imagine.demo.dto.myUser.MyUsernameDTO;
import bar.imagine.demo.dto.myUser.PasswordDTO;
import bar.imagine.demo.repository.CustomerRepository;
import bar.imagine.demo.repository.MyUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private MyUserRepository myUserRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private RegistrationConverter registrationConverter;
    @Mock private ApplicationEventPublisher eventPublisher;
    @Mock private MyUserConverter myUserConverter;

    @InjectMocks
    private RegistrationService registrationService;

    private RegistrationDTO buildRegistrationDto(String username, String email, String password) {
        PasswordDTO pwd = new PasswordDTO(password);
        NewPasswordDetailsDTO npd = NewPasswordDetailsDTO.builder()
            .newPassword(pwd)
            .confirmNewPassword(pwd)
            .build();
        MyUserRegistrationDTO myUserDto = MyUserRegistrationDTO.builder()
            .myUsername(new MyUsernameDTO(username))
            .email(new EmailDTO(email))
            .newPasswordDetails(npd)
            .build();
        return RegistrationDTO.builder()
            .myUser(myUserDto)
            .build();
    }

    @Test
    void register_success_savesEntitiesAndPublishesEvent() {
        RegistrationDTO dto = buildRegistrationDto("newuser", "new@example.com", "SecurePass1!");

        when(myUserRepository.existsByMyUsername(any(MyUsername.class))).thenReturn(false);
        when(myUserRepository.existsByEmail(any(Email.class))).thenReturn(false);
        when(registrationConverter.convertRegistrationDtoToCustomer(dto)).thenReturn(mock(Customer.class));
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$encoded");
        when(customerRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(myUserRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        registrationService.register(dto);

        verify(customerRepository).save(any());
        verify(myUserRepository).save(any());
        ArgumentCaptor<RegistrationSuccessEvent> eventCaptor = ArgumentCaptor.forClass(RegistrationSuccessEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertTrue(eventCaptor.getValue().email().contains("example.com"));
    }

    @Test
    void register_throwsIllegalArgument_whenUsernameAlreadyExists() {
        RegistrationDTO dto = buildRegistrationDto("takenuser", "free@example.com", "SecurePass1!");
        when(myUserRepository.existsByMyUsername(any(MyUsername.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> registrationService.register(dto));
        verify(myUserRepository, never()).save(any());
    }

    @Test
    void register_throwsIllegalArgument_whenEmailAlreadyExists() {
        RegistrationDTO dto = buildRegistrationDto("freeuser", "taken@example.com", "SecurePass1!");
        when(myUserRepository.existsByMyUsername(any(MyUsername.class))).thenReturn(false);
        when(myUserRepository.existsByEmail(any(Email.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> registrationService.register(dto));
        verify(myUserRepository, never()).save(any());
    }
}
