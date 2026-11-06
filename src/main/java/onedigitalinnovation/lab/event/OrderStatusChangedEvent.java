package onedigitalinnovation.lab.event;

import onedigitalinnovation.lab.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderStatusChangedEvent(
        Long orderId,
        OrderStatus previousStatus,
        OrderStatus newStatus,
        LocalDateTime changedAt
) {}




