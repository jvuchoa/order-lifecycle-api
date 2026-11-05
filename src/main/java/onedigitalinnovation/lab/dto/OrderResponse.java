package onedigitalinnovation.lab.dto;

import onedigitalinnovation.lab.domain.Order;
import onedigitalinnovation.lab.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String name,
        String email,
        String address,
        String observation,
        OrderStatus status,
        LocalDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getName(),
                order.getEmail(),
                order.getAddress(),
                order.getObservation(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}