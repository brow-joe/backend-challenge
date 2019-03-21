package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.AbstractedDTO;
import br.com.jonathan.application.dto.RefundPaymentDTO;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity(name = "event")
public class EventEntity extends AbstractedEntity {

    public EventEntity() {
        this(null);
    }

    public EventEntity(UUID id) {
        super(id);
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event", nullable = false)
    private EventTypeEnum event;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "data")
    @Type(type = "text")
    private String data;

    public EventTypeEnum getEvent() {
        return event;
    }

    public EventEntity setEvent(EventTypeEnum event) {
        this.event = event;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public EventEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getData() {
        return data;
    }

    public EventEntity setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public <D extends AbstractedDTO> D toDTO() {
        return null;
    }

    public static EventEntity of(RefundPaymentDTO refund) {
        return new EventEntity()
                .setDate(new Date())
                .setEvent(EventTypeEnum.REFUND)
                .setData(refund.toJson());
    }

}
