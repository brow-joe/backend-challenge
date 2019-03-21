package br.com.jonathan.application.dto;

import java.io.Serializable;

public class RequestRefundDTO implements Serializable {

    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public RequestRefundDTO setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

}
