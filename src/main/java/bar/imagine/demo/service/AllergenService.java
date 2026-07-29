package bar.imagine.demo.service;

import java.util.List;

import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import bar.imagine.demo.repository.AllergenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AllergenService {
    private final AllergenRepository allergenRepository;

    @Transactional(readOnly = true)
    public List<AllergenDTO> getAllAllergens() {
        return allergenRepository.findAll().stream()
            .map(a -> AllergenDTO.builder()
                .id(a.getId())
                .name(new AllergenNameDTO(a.getName().getValue()))
                .iconName(a.getIconName())
                .build())
            .toList();
    }
}
