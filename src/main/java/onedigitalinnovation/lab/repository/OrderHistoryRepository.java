package onedigitalinnovation.lab.repository;

import onedigitalinnovation.lab.domain.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Long> {
    List<OrderHistory> findByOrderIdOrderByChangedAtAsc(Long orderId);

}
