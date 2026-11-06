package onedigitalinnovation.lab.listener;

import onedigitalinnovation.lab.domain.OrderHistory;
import onedigitalinnovation.lab.event.OrderStatusChangedEvent;
import onedigitalinnovation.lab.repository.OrderHistoryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderHistoryListener {

    private final OrderHistoryRepository repository;

    public OrderHistoryListener(OrderHistoryRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void onStatusChanged(OrderStatusChangedEvent event) {
        repository.save(new OrderHistory(
                event.orderId(),
                event.previousStatus(),
                event.newStatus(),
                event.changedAt()
        ));
    }
}