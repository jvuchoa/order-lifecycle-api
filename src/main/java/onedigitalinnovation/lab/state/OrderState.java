package onedigitalinnovation.lab.state;

import onedigitalinnovation.lab.domain.InvalidOrderTransitionException;
import onedigitalinnovation.lab.domain.OrderStatus;

public interface OrderState {
    OrderStatus status();

    default OrderStatus pay() {
        throw new InvalidOrderTransitionException(status(), "pay");
    }

    default OrderStatus send() {
        throw new InvalidOrderTransitionException(status(), "shipped");
    }

    default OrderStatus deliver() {
        throw new InvalidOrderTransitionException(status(), "deliver");
    }

    default OrderStatus cancel() {
        throw new InvalidOrderTransitionException(status(), "cancel");
    }
}
