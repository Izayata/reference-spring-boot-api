package bar.imagine.demo.data.customer;

import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_REQUIRED;

import bar.imagine.demo.data.customer.personalDetails.Firstname;
import bar.imagine.demo.data.customer.personalDetails.Lastname;
import bar.imagine.demo.data.customer.personalDetails.PhoneNumber;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
    @AttributeOverride(name = "firstname.value", column = @Column(name = "FIRSTNAME", nullable = false)),
    @AttributeOverride(name = "lastname.value", column = @Column(name = "LASTNAME", nullable = false)),
    @AttributeOverride(name = "phoneNumber.value", column = @Column(name = "PHONE_NUMBER", nullable = false))
})
public class PersonalDetails {
    @Embedded
    @NotNull(message = ERR_MSG_FIRSTNAME_REQUIRED)
    @Valid
    private Firstname firstname;

    @Embedded
    @NotNull(message = ERR_MSG_LASTNAME_REQUIRED)
    @Valid
    private Lastname lastname;

    @Embedded
    @NotNull(message = ERR_MSG_PHONE_NUMBER_REQUIRED)
    @Valid
    private PhoneNumber phoneNumber;
}
