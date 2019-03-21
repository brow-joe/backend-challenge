package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class RefundPaymentDTO implements Serializable {

    private String paymentId;
    private String ticket;
    private Date requestDate;
    private RequestRefundDTO data;

    public RefundPaymentDTO() {
        this(null, null);
    }

    public RefundPaymentDTO(String paymentId, RequestRefundDTO data) {
        this(paymentId, UUID.randomUUID().toString(), data);
    }

    public RefundPaymentDTO(String paymentId, String ticket, RequestRefundDTO data) {
        this(paymentId, ticket, new Date(), data);
    }

    public RefundPaymentDTO(String paymentId, String ticket, Date requestDate, RequestRefundDTO data) {
        super();
        this.paymentId = paymentId;
        this.ticket = ticket;
        this.requestDate = requestDate;
        this.data = data;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public RefundPaymentDTO setPaymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public String getTicket() {
        return ticket;
    }

    public RefundPaymentDTO setTicket(String ticket) {
        this.ticket = ticket;
        return this;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public RefundPaymentDTO setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public RequestRefundDTO getData() {
        return data;
    }

    public RefundPaymentDTO setData(RequestRefundDTO data) {
        this.data = data;
        return this;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static RefundPaymentDTO of(String id, RequestRefundDTO data) {
        return new RefundPaymentDTO(id, data);
    }

    public static RefundPaymentDTO of(EventEntity event) {
        if (event.getEvent() == EventTypeEnum.REFUND && StringUtils.isNotEmpty(event.getData())) {
            try {
                return new ObjectMapper().readValue(event.getData(), RefundPaymentDTO.class);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

}
