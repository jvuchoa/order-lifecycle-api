package onedigitalinnovation.lab.service;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderBuilder;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(CreateOrderRequest request) {
        Order order = new OrderBuilder()
                .name(request.name())
                .email(request.email())
                .address(request.address())
                .observation(request.observation())
                .build();

        return repository.save(order);
    }
    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order " + id + " not found"));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order pay(Long id) {
        Order order = findById(id);
        order.pay();
        return repository.save(order);
    }

    public Order send(Long id) {
        Order order = findById(id);
        order.send();
        return repository.save(order);
    }

    public Order deliver(Long id) {
        Order order = findById(id);
        order.deliver();
        return repository.save(order);
    }

    public Order cancel(Long id) {
        Order order = findById(id);
        order.cancel();
        return repository.save(order);
    }
}