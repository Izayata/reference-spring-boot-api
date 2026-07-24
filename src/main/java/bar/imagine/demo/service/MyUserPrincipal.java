package bar.imagine.demo.service;

import bar.imagine.demo.data.MyUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MyUserPrincipal implements UserDetails {
    private final MyUser myUser;

    public MyUserPrincipal(MyUser myUser) {
        this.myUser = myUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + myUser.getRole().name()));
    }

    @Override
    public String getPassword() {
        return myUser.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return myUser.getMyUsername().getValue();
    }
}
