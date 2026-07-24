package bar.imagine.demo.controller;

import bar.imagine.demo.dto.OrderDTO;
import bar.imagine.demo.request.data.CreateOrderRequestData;
import bar.imagine.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequestData orderRequest) {
        OrderDTO created = orderService.createOrder(orderRequest);
        log.debug("Order created for authenticated customer");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/guest")
    public ResponseEntity<OrderDTO> createGuestOrder(@Valid @RequestBody CreateOrderRequestData orderRequest) {
        OrderDTO created = orderService.createGuestOrder(orderRequest);
        log.debug("Guest order created");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
