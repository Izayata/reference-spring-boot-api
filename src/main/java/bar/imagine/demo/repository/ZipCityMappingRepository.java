package bar.imagine.demo.repository;

import bar.imagine.demo.data.address.ZipCityMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCityMappingRepository extends JpaRepository<ZipCityMapping, Long> {
    Optional<ZipCityMapping> findByZipCode_Value(String zipCodeValue);
}
