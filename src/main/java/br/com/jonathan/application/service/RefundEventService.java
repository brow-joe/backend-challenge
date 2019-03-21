package br.com.jonathan.application.service;

import br.com.jonathan.application.dto.RefundPaymentDTO;

import java.util.concurrent.CompletableFuture;

public interface RefundEventService {

    public CompletableFuture<RefundPaymentDTO> emit(RefundPaymentDTO refund);

}
