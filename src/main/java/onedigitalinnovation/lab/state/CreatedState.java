package onedigitalinnovation.lab.state;

import onedigitalinnovation.lab.domain.OrderStatus;

public class  CreatedState implements OrderState {
    public OrderStatus status() { return OrderStatus.CREATED; }
    public OrderStatus pay()    { return OrderStatus.PAID; }
    public OrderStatus cancel() { return OrderStatus.CANCELED; }}
