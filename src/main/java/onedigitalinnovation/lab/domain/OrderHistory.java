package onedigitalinnovation.lab.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus newStatus;

    private LocalDateTime changedAt;

    protected OrderHistory() {
    }

    public OrderHistory(Long orderId, OrderStatus previousStatus, OrderStatus newStatus, LocalDateTime changedAt) {
        this.orderId = orderId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public OrderStatus getPreviousStatus() { return previousStatus; }
    public OrderStatus getNewStatus() { return newStatus; }
    public LocalDateTime getChangedAt() { return changedAt; }
}