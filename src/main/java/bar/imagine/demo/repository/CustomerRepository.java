package bar.imagine.demo.repository;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(Email email);

    Optional<Customer> findByMyUserId(Long myUserId);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.shippingAddresses")
    List<Customer> findAllWithShippingAddresses();
}
