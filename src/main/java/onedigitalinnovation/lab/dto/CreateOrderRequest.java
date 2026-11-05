package onedigitalinnovation.lab.dto;

public record CreateOrderRequest(
        String name,
        String email,
        String address,
        String observation
) {}