package bar.imagine.demo.service;

import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CURRENT_PASSWORD_INCORRECT;

import bar.imagine.demo.converter.MyUserConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.PasswordChangeDTO;
import bar.imagine.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;
    private final MyUserConverter myUserConverter;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerRepository customerRepository;

    // MyUser.getCustomer() is a lazy association loaded on the authenticated principal at login
    // time (see MyUserPrincipal); its persistence context is long closed by now, so any access
    // would throw LazyInitializationException. Fetching Customer fresh, inside this method's own
    // transaction, keeps it attached through the converter call below.
    @Transactional(readOnly = true)
    public MyUserDTO getAuthenticatedUserProfileAsMyUserDTO() {
        MyUser myUser = userService.getAuthenticatedUser();
        Customer freshCustomer = customerRepository.findByMyUserId(myUser.getId())
            .orElseThrow(() -> new NoSuchElementException("Customer not found for authenticated user"));
        myUser.setCustomer(freshCustomer);
        return myUserConverter.convertMyUserToMyUserDto(myUser);
    }

    @Transactional
    public void updateAuthenticatedUserPassword(PasswordChangeDTO passwordChangeDto) {
        MyUser authenticatedUser = userService.getAuthenticatedUser();
        boolean doCurrentPasswordMatchesExistingPassword = passwordEncoder.matches(passwordChangeDto.getCurrentPassword().getValue(), authenticatedUser.getPassword().getValue());
        if(!doCurrentPasswordMatchesExistingPassword) {
            throw new IllegalArgumentException(ERR_MSG_CURRENT_PASSWORD_INCORRECT);
        }

        authenticatedUser.setPassword(new Password(passwordEncoder.encode(passwordChangeDto.getNewPasswordDetails().getNewPassword().getValue())));
        userService.saveMyUser(authenticatedUser);

        eventPublisher.publishEvent(new PasswordChangedEvent(
            authenticatedUser.getEmail().getValue(),
            authenticatedUser.getMyUsername().getValue()
        ));
    }
}
