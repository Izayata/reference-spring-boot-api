package bar.imagine.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenValidationResult {
    private final boolean valid;
    private final boolean expired;
    private final boolean used;
    private final String message;
}
