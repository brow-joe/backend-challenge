package br.com.jonathan.application.handler;

import br.com.jonathan.application.dto.RefundPaymentDTO;
import br.com.jonathan.domain.entity.EventEntity;
import br.com.jonathan.domain.enumeration.EventTypeEnum;
import br.com.jonathan.domain.repository.EventRepository;
import br.com.jonathan.domain.repository.exception.EntityNotFoundException;
import br.com.jonathan.domain.service.PaymentService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class EventHandler {
    private static final Logger logger = LogManager.getLogger(EventHandler.class);

    @Inject
    private EventRepository repository;
    @Inject
    private PaymentService service;

    @Transactional(rollbackFor = Throwable.class)
    public synchronized void execute() {
        List<EventEntity> events = repository.findAllByEvent(EventTypeEnum.REFUND);
        if (CollectionUtils.isNotEmpty(events)) {
            events.forEach(this::execute);
        }
    }

    private void execute(EventEntity event) {
        RefundPaymentDTO refund = RefundPaymentDTO.of(event);
        if (Objects.nonNull(refund)) {
            execute(event.getId().toString(), refund);
        }
    }

    private void execute(String eventId, RefundPaymentDTO refund) {
        try {
            if (StringUtils.isNotEmpty(refund.getPaymentId())) {
                service.refund(refund.getPaymentId());
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                repository.delete(eventId);
            } catch (EntityNotFoundException e) {
                logger.error(e);
            }
        }
    }

}
