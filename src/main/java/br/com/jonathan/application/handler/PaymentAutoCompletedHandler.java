package br.com.jonathan.application.handler;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.service.PaymentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentAutoCompletedHandler {

    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private PaymentService service;

    @Transactional(rollbackFor = Throwable.class)
    public synchronized void execute() {
        List<PaymentEntity> payments = paymentRepository.findAllByStatus(PaymentTypeEnum.CREATED);
        if (CollectionUtils.isNotEmpty(payments)) {
            payments.forEach(this::execute);
        }
    }

    private void execute(PaymentEntity payment) {
        if (Objects.nonNull(payment) && payment.hasCompleted(new Date())) {
            service.completed(payment);
        }
    }

}
