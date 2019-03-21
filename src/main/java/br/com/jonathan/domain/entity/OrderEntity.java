package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.OrderDTO;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity(name = "store_order")
public class OrderEntity extends AbstractedEntity {

    public OrderEntity() {
        this(null);
    }

    public OrderEntity(UUID id) {
        super(id);
    }

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "confirmation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderTypeEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public boolean isValid() {
        boolean isValid = StringUtils.isNotEmpty(address) && Objects.nonNull(status);
        if (isValid && CollectionUtils.isNotEmpty(items)) {
            for (OrderItemEntity item : items) {
                if (!item.isValid()) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    public boolean hasCompleted(Date date) {
        if (Objects.nonNull(confirmation)) {
            DateTime limit = new DateTime(confirmation).plusDays(10);
            DateTime current = new DateTime(date).plus(1);
            return current.isAfter(limit);

        }
        return false;
    }

    @PrePersist
    protected void onCreate() {
        if (Objects.isNull(confirmation)) {
            confirmation = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (Objects.isNull(confirmation)) {
            confirmation = new Date();
        }
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
