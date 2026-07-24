package bar.imagine.demo.service;

import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.repository.MyUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private MyUserRepository myUserRepository;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void saveMyUser_delegatesToRepository() {
        MyUser user = mock(MyUser.class);
        when(myUserRepository.save(user)).thenReturn(user);

        assertSame(user, userService.saveMyUser(user));
    }

    @Test
    void loadUserByUsername_returnsMyUserPrincipal_whenFound() {
        MyUser user = MyUser.builder().myUsername(new MyUsername("testuser")).build();
        when(myUserRepository.findByMyUsername(new MyUsername("testuser"))).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("testuser");

        assertInstanceOf(MyUserPrincipal.class, result);
        assertSame(user, ((MyUserPrincipal) result).getMyUser());
    }

    @Test
    void loadUserByUsername_throwsUsernameNotFound_whenMissing() {
        when(myUserRepository.findByMyUsername(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("ghost"));
    }

    @Test
    void getAuthenticatedUser_returnsMyUser_whenPrincipalIsMyUserPrincipal() {
        MyUser user = MyUser.builder().myUsername(new MyUsername("testuser")).build();
        MyUserPrincipal principal = new MyUserPrincipal(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertSame(user, userService.getAuthenticatedUser());
    }

    @Test
    void getAuthenticatedUser_throws_whenNoAuthentication() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getAuthenticatedUser());
    }

    @Test
    void getAuthenticatedUser_throws_whenPrincipalIsNotMyUserPrincipal() {
        Authentication auth = new UsernamePasswordAuthenticationToken("someOtherPrincipal", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(UsernameNotFoundException.class, () -> userService.getAuthenticatedUser());
    }
}
