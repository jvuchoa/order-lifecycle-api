package onedigitalinnovation.lab.controller;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderStatus;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.dto.OrderResponse;
import onedigitalinnovation.lab.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setName(request.name());
        order.setEmail(request.email());
        order.setAddress(request.address());
        order.setObservation(request.observation());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order saved = repository.save(order);
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
}