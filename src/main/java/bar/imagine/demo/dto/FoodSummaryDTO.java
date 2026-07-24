package bar.imagine.demo.dto;

import bar.imagine.demo.dto.food.FoodNameDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSummaryDTO {
    private Long id;
    private FoodNameDTO foodName;
}
