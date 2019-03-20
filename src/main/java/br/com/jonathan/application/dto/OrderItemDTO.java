package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.OrderItemEntity;

import java.util.UUID;

public class OrderItemDTO extends AbstractedDTO {

    private String description;
    private Double price;
    private Long quantity;

    public OrderItemDTO() {
        this(null, null, null);
    }

    public OrderItemDTO(String description, Double price, Long quantity) {
        super();
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public OrderItemDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OrderItemDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public OrderItemDTO setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public static OrderItemDTO of(String description, Double price, Long quantity) {
        return new OrderItemDTO(description, price, quantity);
    }

    @Override
    public OrderItemEntity toEntity(String id) {
        return OrderItemEntity.of(UUID.fromString(id), description, price, quantity);
    }

    @Override
    public OrderItemEntity toEntity() {
        return OrderItemEntity.of(description, price, quantity);
    }
}
