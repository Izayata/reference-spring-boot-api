package bar.imagine.demo.request.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartRequestData {
    @NotNull
    @NotEmpty
    private List<Long> ids;
}
