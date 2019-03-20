package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "store_order")
public class OrderEntity extends AbstractedEntity {

    public OrderEntity() {
        this(null);
    }

    public OrderEntity(UUID id) {
        super(id);
    }

    @Column(name = "address")
    private String address;

    @Column(name = "confirmation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderTypeEnum status;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OrderItemEntity> items = new HashSet<>();

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Date getConfirmation() {
        return confirmation;
    }

    public OrderEntity setConfirmation(Date confirmation) {
        this.confirmation = confirmation;
        return this;
    }

    public OrderTypeEnum getStatus() {
        return status;
    }

    public OrderEntity setStatus(OrderTypeEnum status) {
        this.status = status;
        return this;
    }

    public StoreEntity getStore() {
        return store;
    }

    public OrderEntity setStore(StoreEntity store) {
        this.store = store;
        return this;
    }

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(Set<OrderItemEntity> items) {
        this.items = items;
        return this;
    }

    @Override
    public OrderDTO toDTO() {
        return OrderDTO.of(address, confirmation, status, store, items);
    }

    public static OrderEntity of(String address, Date confirmation, OrderTypeEnum status, StoreEntity store, Set<OrderItemEntity> items) {
        return of(null, address, confirmation, status, store, items);
    }

    public static OrderEntity of(UUID id, String address, Date confirmation, OrderTypeEnum status, StoreEntity store, Set<OrderItemEntity> items) {
        return new OrderEntity(id)
                .setAddress(address)
                .setConfirmation(confirmation)
                .setStatus(status)
                .setStore(store)
                .setItems(items);
    }

}
