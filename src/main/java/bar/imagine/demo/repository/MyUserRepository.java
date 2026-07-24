package bar.imagine.demo.repository;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.myUser.MyUsername;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(Email email);
    boolean existsByMyUsername(MyUsername myUsername);

    boolean existsByEmail(Email email);

    Optional<MyUser> findByMyUsername(MyUsername myUsername);
}
