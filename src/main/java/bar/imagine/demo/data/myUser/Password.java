package bar.imagine.demo.data.myUser;

import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_VALUE_REQUIRED;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class Password {
    @JsonIgnore
    @ToString.Exclude
    @NotBlank(message = ERR_MSG_PASSWORD_VALUE_REQUIRED)
    private final String value;

    @JsonCreator
    public Password(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Password() {
        this.value = null;
    }
}
