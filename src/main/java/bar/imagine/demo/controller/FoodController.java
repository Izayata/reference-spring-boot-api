package bar.imagine.demo.controller;

import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.dto.FoodDetailsDTO;
import bar.imagine.demo.dto.FoodDTO;
import bar.imagine.demo.dto.MenuItemDTO;
import bar.imagine.demo.dto.ShoppingCartItemDTO;
import bar.imagine.demo.request.data.ShoppingCartRequestData;
import bar.imagine.demo.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public List<MenuItemDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    @PostMapping("/cart")
    public List<ShoppingCartItemDTO> getShoppingCartItems(@Valid @RequestBody ShoppingCartRequestData requestData) {
        return foodService.getShoppingCartItems(requestData);
    }

    @GetMapping("/menu/{placeToBuy}")
    public List<MenuItemDTO> getMenuItemsByPlaceToBuy(@PathVariable PlaceToBuyEnum placeToBuy) {
        return foodService.getMenuItemsByPlaceToBuy(placeToBuy);
    }

    @GetMapping("/{id}")
    public FoodDetailsDTO getFoodDetailsById(@PathVariable Long id) {
        return foodService.getFoodDetailsById(id);
    }

    @PostMapping
    public ResponseEntity<FoodDetailsDTO> createFood(@Valid @RequestBody FoodDTO foodDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(foodDto));
    }
}
