package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.entity.OrderItemEntity;
import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class OrderDTO extends AbstractedDTO {

    private String address;
    private Date confirmation;
    private OrderTypeEnum status;
    private String storeId;
    private Set<OrderItemDTO> items = new HashSet<>();

    public OrderDTO() {
        this(null, null, null, null, new HashSet<>());
    }

    public OrderDTO(String address, Date confirmation, OrderTypeEnum status, String storeId, Set<OrderItemDTO> items) {
        super();
        this.address = address;
        this.confirmation = confirmation;
        this.status = status;
        this.storeId = storeId;
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Date getConfirmation() {
        return confirmation;
    }

    public OrderDTO setConfirmation(Date confirmation) {
        this.confirmation = confirmation;
        return this;
    }

    public OrderTypeEnum getStatus() {
        return status;
    }

    public OrderDTO setStatus(OrderTypeEnum status) {
        this.status = status;
        return this;
    }

    public String getStoreId() {
        return storeId;
    }

    public OrderDTO setStoreId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public OrderDTO setItems(Set<OrderItemDTO> items) {
        this.items = items;
        return this;
    }

    public static OrderDTO of(String address, Date confirmation, OrderTypeEnum status, StoreEntity store, Set<OrderItemEntity> items) {
        String storeId = null;
        if (Objects.nonNull(store) && Objects.nonNull(store.getId())) {
            storeId = store.getId().toString();
        }
        Set<OrderItemDTO> dtos = new HashSet<>();
        if (CollectionUtils.isNotEmpty(items)) {
            dtos = items.stream().map(OrderItemEntity::toDTO).collect(Collectors.toSet());
        }
        return new OrderDTO(address, confirmation, status, storeId, dtos);
    }

    @Override
    public OrderEntity toEntity() {
        return OrderEntity.of(address, confirmation, status, getStore(), getOrderItems());
    }

    @Override
    public OrderEntity toEntity(String id) {
        return OrderEntity.of(UUID.fromString(id), address, confirmation, status, getStore(), getOrderItems());
    }

    @JsonIgnore
    private StoreEntity getStore() {
        if (StringUtils.isNotEmpty(storeId)) {
            return new StoreEntity(UUID.fromString(storeId));
        }
        return null;
    }

    @JsonIgnore
    private Set<OrderItemEntity> getOrderItems() {
        Set<OrderItemEntity> entities = new HashSet<>();
        if (CollectionUtils.isNotEmpty(items)) {
            entities = items.stream().map(OrderItemDTO::toEntity).collect(Collectors.toSet());
        }
        return entities;
    }
}
