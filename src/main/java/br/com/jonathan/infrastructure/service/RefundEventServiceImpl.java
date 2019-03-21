package br.com.jonathan.infrastructure.service;

import br.com.jonathan.application.dto.RefundPaymentDTO;
import br.com.jonathan.application.service.RefundEventService;
import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Service
public class RefundEventServiceImpl implements RefundEventService {
    private static final Logger logger = LogManager.getLogger(RefundEventServiceImpl.class);

    @Inject
    private EventRepository repository;

    @Async
    @Override
    public CompletableFuture<RefundPaymentDTO> emit(RefundPaymentDTO refund) {
        EventEntity event = repository.save(EventEntity.of(refund));
        logger.info("Create new event={}", event);
        return CompletableFuture.completedFuture(refund);
    }

}
