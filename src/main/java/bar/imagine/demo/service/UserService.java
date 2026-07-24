package bar.imagine.demo.service;

import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final MyUserRepository myUserRepository;

    public MyUser saveMyUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepository.findByMyUsername(new MyUsername(username));
        if (user.isPresent()) {
            return new MyUserPrincipal(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public MyUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof MyUserPrincipal principal) {
            return principal.getMyUser();
        }
        log.debug("No authenticated user found");
        throw new UsernameNotFoundException("Authenticated user not found");
    }

}
