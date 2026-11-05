package onedigitalinnovation.lab.service;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderBuilder;
import onedigitalinnovation.lab.dto.CreateOrderRequest;
import onedigitalinnovation.lab.repository.OrderRepository;
import org.springframework.stereotype.Service;

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
}