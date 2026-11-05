package onedigitalinnovation.lab.controller;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderStatus;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.dto.OrderResponse;
import onedigitalinnovation.lab.repository.OrderRepository;
import onedigitalinnovation.lab.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final OrderService orderService;


    public OrderController(OrderRepository repository,OrderService orderService) {
        this.repository = repository;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        Order saved = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(saved));
    }
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return OrderResponse.from(findOrThrow(id));
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    @PatchMapping("/{id}/pay")
    public OrderResponse pay(@PathVariable Long id) {
        Order order = findOrThrow(id);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw invalidTransition(order, "pay");
        }
        order.setStatus(OrderStatus.PAID);
        return OrderResponse.from(repository.save(order));
    }

    @PatchMapping("/{id}/send")
    public OrderResponse send(@PathVariable Long id) {
        Order order = findOrThrow(id);
        if (order.getStatus() != OrderStatus.PAID) {
            throw invalidTransition(order, "send");
        }
        order.setStatus(OrderStatus.CREATED);
        return OrderResponse.from(repository.save(order));
    }

    @PatchMapping("/{id}/deliver")
    public OrderResponse deliver(@PathVariable Long id) {
        Order order = findOrThrow(id);
        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw invalidTransition(order, "deliver");
        }
        order.setStatus(OrderStatus.DELIVERED);
        return OrderResponse.from(repository.save(order));
    }

    @PatchMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        Order order = findOrThrow(id);
        if (order.getStatus() != OrderStatus.CREATED && order.getStatus() != OrderStatus.PAID) {
            throw invalidTransition(order, "cancel");
        }
        order.setStatus(OrderStatus.CANCELED);
        return OrderResponse.from(repository.save(order));
    }

    private Order findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order " + id + " not found"));
    }

    private ResponseStatusException invalidTransition(Order order, String action) {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cannot " + action + " an order with status " + order.getStatus());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidation(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}