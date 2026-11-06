package onedigitalinnovation.lab.dto;

import onedigitalinnovation.lab.domain.OrderHistory;
import onedigitalinnovation.lab.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderHistoryResponse(
        OrderStatus previousStatus,
        OrderStatus newStatus,
        LocalDateTime changedAt
) {
    public static OrderHistoryResponse from(OrderHistory history) {
        return new OrderHistoryResponse(
                history.getPreviousStatus(),
                history.getNewStatus(),
                history.getChangedAt()
        );
    }
}
