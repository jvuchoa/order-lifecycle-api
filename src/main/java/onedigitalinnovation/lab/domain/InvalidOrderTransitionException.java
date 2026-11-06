package onedigitalinnovation.lab.domain;

public class InvalidOrderTransitionException extends RuntimeException{
    public InvalidOrderTransitionException(OrderStatus status, String action) {
        super("Cannot " + action + " an order with status " + status);
    }
}
