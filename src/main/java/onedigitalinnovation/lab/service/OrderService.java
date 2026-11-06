package onedigitalinnovation.lab.service;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderBuilder;
import onedigitalinnovation.lab.domain.OrderHistory;
import onedigitalinnovation.lab.domain.OrderStatus;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.event.OrderStatusChangedEvent;
import onedigitalinnovation.lab.repository.OrderHistoryRepository;
import onedigitalinnovation.lab.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderHistoryRepository historyRepository;
    private final ApplicationEventPublisher publisher;

    public OrderService(OrderRepository repository,
                        OrderHistoryRepository historyRepository,
                        ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.publisher = publisher;
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
        return transition(id, Order::pay);
    }

    public Order send(Long id) {
        return transition(id, Order::send);
    }

    public Order deliver(Long id) {
        return transition(id, Order::deliver);
    }

    public Order cancel(Long id) {
        return transition(id, Order::cancel);
    }

    public List<OrderHistory> findHistory(Long id) {
        findById(id); // garante 404 se o pedido não existir
        return historyRepository.findByOrderIdOrderByChangedAtAsc(id);
    }

    private Order transition(Long id, Consumer<Order> action) {
        Order order = findById(id);
        OrderStatus previous = order.getStatus();

        action.accept(order);
        Order saved = repository.save(order);

        publisher.publishEvent(new OrderStatusChangedEvent(
                saved.getId(), previous, saved.getStatus(), LocalDateTime.now()));
        return saved;
    }
}