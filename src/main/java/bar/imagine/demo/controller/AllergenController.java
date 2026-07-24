package bar.imagine.demo.controller;

import java.util.List;

import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.service.AllergenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/allergens")
@RequiredArgsConstructor
public class AllergenController {
    private final AllergenService allergenService;

    @GetMapping
    public List<AllergenDTO> getAllAllergens() {
        return allergenService.getAllAllergens();
    }
}
