package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PaymentDTO extends AbstractedDTO {

    private PaymentTypeEnum status;
    private String creditCard;
    private Date payment;
    private String orderId;

    public PaymentDTO() {
        this(null, null, null, null);
    }

    public PaymentDTO(PaymentTypeEnum status, String creditCard, Date payment, String orderId) {
        super();
        this.status = status;
        this.creditCard = creditCard;
        this.payment = payment;
        this.orderId = orderId;
    }

    public PaymentTypeEnum getStatus() {
        return status;
    }

    public PaymentDTO setStatus(PaymentTypeEnum status) {
        this.status = status;
        return this;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public PaymentDTO setCreditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public Date getPayment() {
        return payment;
    }

    public PaymentDTO setPayment(Date payment) {
        this.payment = payment;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public PaymentDTO setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public static PaymentDTO of(PaymentTypeEnum status, String creditCard, Date payment, OrderEntity order) {
        String orderId = null;
        if (Objects.nonNull(order) && Objects.nonNull(order.getId())) {
            orderId = order.getId().toString();
        }
        return new PaymentDTO(status, creditCard, payment, orderId);
    }

    @Override
    public PaymentEntity toEntity() {
        return PaymentEntity.of(status, creditCard, payment, getOrder());
    }

    @Override
    public PaymentEntity toEntity(String id) {
        return PaymentEntity.of(UUID.fromString(id), status, creditCard, payment, getOrder());
    }

    @JsonIgnore
    private OrderEntity getOrder() {
        if (StringUtils.isNotEmpty(orderId)) {
            return new OrderEntity(UUID.fromString(orderId));
        }
        return null;
    }

}
