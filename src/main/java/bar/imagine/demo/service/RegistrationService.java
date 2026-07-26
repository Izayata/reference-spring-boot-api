package bar.imagine.demo.service;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_ALREADY_EXISTS;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_IS_TAKEN;

import bar.imagine.demo.converter.MyUserConverter;
import bar.imagine.demo.converter.RegistrationConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.data.outbox.EmailType;
import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.RegistrationDTO;
import bar.imagine.demo.repository.CustomerRepository;
import bar.imagine.demo.repository.EmailOutboxRepository;
import bar.imagine.demo.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationConverter registrationConverter;
    private final EmailOutboxRepository emailOutboxRepository;
    private final EmailService emailService;
    private final MyUserConverter myUserConverter;

    @Transactional
    public MyUserDTO register(RegistrationDTO registrationDto) {
        //USERNAME CHECK
        var username = new MyUsername(registrationDto.getMyUser().getMyUsername().getValue());
        if (myUserRepository.existsByMyUsername(username)) {
            throw new IllegalArgumentException(ERR_MSG_USERNAME_IS_TAKEN);
        }

        //EMAIL CHECK
        var email = new Email(registrationDto.getMyUser().getEmail().getValue());
        if (myUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(ERR_MSG_EMAIL_ALREADY_EXISTS);
        }

        Customer customerToSave = registrationConverter.convertRegistrationDtoToCustomer(registrationDto);

        //BUILD MY_USER ENTITY AND ENCODE PASSWORD
        MyUser myUserToSave = MyUser.builder()
            .myUsername(username)
            .password(new Password(passwordEncoder.encode(registrationDto.getMyUser().getNewPasswordDetails().getNewPassword().getValue())))
            .email(email)
            .customer(customerToSave)
            .build();

        // SET MY_USER IN CUSTOMER ENTITY
        customerToSave.setMyUser(myUserToSave);

        // SAVE MY_USER AND CUSTOMER (MyUser first: Customer.myUser is the non-nullable owning FK)
        MyUser registeredMyUser = myUserRepository.save(myUserToSave);
        customerRepository.save(customerToSave);

        // WRITE OUTBOX ROW — welcome email is sent asynchronously by EmailOutboxWorker
        EmailContent emailContent = emailService.buildRegistrationSuccessfulEmail(username.getValue());
        emailOutboxRepository.save(EmailOutbox.builder()
            .recipientEmail(email.getValue())
            .emailType(EmailType.REGISTRATION_SUCCESS)
            .subject(emailContent.subject())
            .body(emailContent.body())
            .build());

        return myUserConverter.convertMyUserToMyUserDto(registeredMyUser);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(MyUsername myUsername) {
        return myUserRepository.existsByMyUsername(myUsername);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(Email email) {
        return myUserRepository.existsByEmail(email);
    }
}
