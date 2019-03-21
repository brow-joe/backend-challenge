package br.com.jonathan.domain.service;

import br.com.jonathan.domain.entity.PaymentEntity;

public interface PaymentService {

    public void refund(String id);

    public void completed(PaymentEntity payment);

}
