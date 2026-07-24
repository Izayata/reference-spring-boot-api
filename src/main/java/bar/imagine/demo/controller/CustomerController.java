package bar.imagine.demo.controller;

import java.util.List;

import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
import bar.imagine.demo.dto.customer.AddressDTO;
import jakarta.validation.Valid;

import bar.imagine.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/v1/customers")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/v1/customer/billing-address")
    public ResponseEntity<CustomerDTO> updateBillingAddress(@Valid @RequestBody AddressDTO addressDto) {
        return ResponseEntity.ok(customerService.updateBillingAddress(addressDto));
    }

    @PutMapping("/v1/customer/shipping-address")
    public ResponseEntity<CustomerDTO> updateDefaultShippingAddress(@Valid @RequestBody AddressDTO addressDto) {
        return ResponseEntity.ok(customerService.updateDefaultShippingAddress(addressDto));
    }

    @PatchMapping("/v1/customer/personal-details")
    public ResponseEntity<CustomerDTO> updatePersonalDetails(@Valid @RequestBody PersonalDetailsDTO personalDetailsDto) {
        return ResponseEntity.ok(customerService.updatePersonalDetails(personalDetailsDto));
    }
}
