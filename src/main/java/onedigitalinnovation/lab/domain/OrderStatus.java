package onedigitalinnovation.lab.domain;

import onedigitalinnovation.lab.state.*;

public enum OrderStatus {

    CREATED(new CreatedState()),
    PAID(new PaidState()),
    SHIPPED(new ShippedState()),
    DELIVERED(new DeliveredState()),
    CANCELED(new CanceledState());

    private final OrderState state;

    OrderStatus(OrderState state) {
        this.state = state;
    }

    public OrderState state() {
        return state;
    }
}