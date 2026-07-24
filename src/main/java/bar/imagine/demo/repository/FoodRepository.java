package bar.imagine.demo.repository;

import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByPlaceToBuy(PlaceToBuyEnum placeToBuy);

    @Query("SELECT DISTINCT f FROM Food f LEFT JOIN FETCH f.allergens")
    List<Food> findAllWithAllergens();

    @Query("SELECT DISTINCT f FROM Food f LEFT JOIN FETCH f.allergens WHERE f.placeToBuy = :placeToBuy")
    List<Food> findByPlaceToBuyWithAllergens(@Param("placeToBuy") PlaceToBuyEnum placeToBuy);

    @Query("SELECT DISTINCT f FROM Food f LEFT JOIN FETCH f.allergens LEFT JOIN FETCH f.ingredients WHERE f.id = :id")
    Optional<Food> findByIdWithAllRelations(@Param("id") Long id);
}
