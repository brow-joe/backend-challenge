package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.PaymentDTO;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "order_payment")
public class PaymentEntity extends AbstractedEntity {

    public PaymentEntity() {
        this(null);
    }

    public PaymentEntity(UUID id) {
        super(id);
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentTypeEnum status;

    @NotNull
    @Column(name = "credit_card", nullable = false)
    private String creditCard;

    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_order_id")
    private OrderEntity order;

    public PaymentTypeEnum getStatus() {
        return status;
    }

    public PaymentEntity setStatus(PaymentTypeEnum status) {
        this.status = status;
        return this;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public PaymentEntity setCreditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public Date getPayment() {
        return payment;
    }

    public PaymentEntity setPayment(Date payment) {
        this.payment = payment;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public PaymentEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }

    public boolean isValid() {
        return Objects.nonNull(status) && StringUtils.isNotEmpty(creditCard);
    }

    public boolean hasCompleted(Date date) {
        OrderEntity order = getOrder();
        if (Objects.nonNull(order)) {
            return order.hasCompleted(date);
        }
        return false;
    }

    @Override
    public PaymentDTO toDTO() {
        return PaymentDTO.of(status, creditCard, payment, order);
    }

    public static PaymentEntity of(PaymentTypeEnum status, String creditCard, Date payment, OrderEntity order) {
        return of(null, status, creditCard, payment, order);
    }

    public static PaymentEntity of(UUID id, PaymentTypeEnum status, String creditCard, Date payment, OrderEntity order) {
        return new PaymentEntity(id)
                .setStatus(status)
                .setCreditCard(creditCard)
                .setPayment(payment)
                .setOrder(order);
    }
}
