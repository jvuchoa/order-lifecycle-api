package onedigitalinnovation.lab.domain;

import java.time.LocalDateTime;

public class OrderBuilder {

    private String name;
    private String email;
    private String address;
    private String observation;

    public OrderBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OrderBuilder email(String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder address(String address) {
        this.address = address;
        return this;
    }

    public OrderBuilder observation(String observation) {
        this.observation = observation;
        return this;
    }

    public Order build() {
        requireText(name, "name");
        requireText(email, "email");
        requireText(address, "address");

        Order order = new Order();
        order.setName(name);
        order.setEmail(email);
        order.setAddress(address);
        order.setObservation(observation);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Field '" + field + "' is required");
        }
    }
}