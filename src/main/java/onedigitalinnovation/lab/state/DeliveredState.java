package onedigitalinnovation.lab.state;

import onedigitalinnovation.lab.domain.OrderStatus;

public class DeliveredState implements OrderState {
    public OrderStatus status() { return OrderStatus.DELIVERED; }

}
