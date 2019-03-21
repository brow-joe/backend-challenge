package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.OrderItemDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "order_item")
public class OrderItemEntity extends AbstractedEntity {

    public OrderItemEntity() {
        this(null);
    }

    public OrderItemEntity(UUID id) {
        super(id);
    }

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public String getDescription() {
        return description;
    }

    public OrderItemEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OrderItemEntity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public OrderItemEntity setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(description) &&
                Objects.nonNull(price) &&
                Objects.nonNull(quantity);
    }

    @Override
    public OrderItemDTO toDTO() {
        return OrderItemDTO.of(description, price, quantity);
    }

    public static OrderItemEntity of(String description, Double price, Long quantity) {
        return of(null, description, price, quantity);
    }

    public static OrderItemEntity of(UUID id, String description, Double price, Long quantity) {
        return new OrderItemEntity(id)
                .setDescription(description)
                .setPrice(price)
                .setQuantity(quantity);
    }

}
