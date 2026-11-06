package onedigitalinnovation.lab.state;

import onedigitalinnovation.lab.domain.OrderStatus;

public class CanceledState implements OrderState {
    public OrderStatus status(){
        return OrderStatus.CANCELED;
    }
}
