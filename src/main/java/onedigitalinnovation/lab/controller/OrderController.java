package onedigitalinnovation.lab.controller;

import onedigitalinnovation.lab.domain.InvalidOrderTransitionException;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.dto.OrderResponse;
import onedigitalinnovation.lab.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.from(service.create(request)));
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return OrderResponse.from(service.findById(id));
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return service.findAll().stream().map(OrderResponse::from).toList();
    }

    @PatchMapping("/{id}/pay")
    public OrderResponse pay(@PathVariable Long id) {
        return OrderResponse.from(service.pay(id));
    }

    @PatchMapping("/{id}/send")
    public OrderResponse send(@PathVariable Long id) {
        return OrderResponse.from(service.send(id));
    }

    @PatchMapping("/{id}/deliver")
    public OrderResponse deliver(@PathVariable Long id) {
        return OrderResponse.from(service.deliver(id));
    }

    @PatchMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        return OrderResponse.from(service.cancel(id));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidation(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(InvalidOrderTransitionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTransition(InvalidOrderTransitionException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
    }
}