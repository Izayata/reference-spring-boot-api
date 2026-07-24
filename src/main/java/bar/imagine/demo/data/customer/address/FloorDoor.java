package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.FLOOR_DOOR_ALLOWED_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.FLOOR_DOOR_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.FLOOR_DOOR_MIN_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class FloorDoor {
    @NotBlank(message = ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)
    @Size(min = FLOOR_DOOR_MIN_LENGTH, max = FLOOR_DOOR_MAX_LENGTH, message = ERR_MSG_FLOOR_DOOR_LENGTH)
    @Pattern(regexp = FLOOR_DOOR_ALLOWED_FORMAT, message = ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)
    private final String value;

    @JsonCreator
    public FloorDoor(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected FloorDoor() {
        this.value = null;
    }
}
