package bar.imagine.demo.data;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.MyUserUtils.ERR_MSG_CUSTOMER_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.data.myUser.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MY_USERS")
@AttributeOverrides({
    @AttributeOverride(name = "myUsername.value", column = @Column(name = "MY_USERNAME", nullable = false)),
    @AttributeOverride(name = "password.value", column = @Column(name = "PASSWORD", nullable = false)),
    @AttributeOverride(name = "email.value", column = @Column(name = "EMAIL", nullable = false, unique = true)),
})
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_EMAIL_REQUIRED)
    private Email email;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_USERNAME_REQUIRED)
    private MyUsername myUsername;

    @JsonIgnore
    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_PASSWORD_REQUIRED)
    @ToString.Exclude
    private Password password;

    @OneToOne(mappedBy = "myUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @NotNull(message = ERR_MSG_CUSTOMER_REQUIRED)
    @ToString.Exclude
    private Customer customer;

    @OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<PasswordResetToken> passwordResetTokens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    @Builder.Default
    private Role role = Role.USER;
}
