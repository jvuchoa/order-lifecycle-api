package onedigitalinnovation.lab.state;

import onedigitalinnovation.lab.domain.OrderStatus;

public class ShippedState implements OrderState {
    public OrderStatus status()  { return OrderStatus.SHIPPED; }
    public OrderStatus deliver() { return OrderStatus.DELIVERED; }

}
