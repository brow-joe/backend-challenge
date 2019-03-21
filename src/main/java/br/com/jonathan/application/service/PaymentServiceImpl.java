package br.com.jonathan.application.service;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.enumeration.OrderTypeEnum;
import br.com.jonathan.domain.enumeration.PaymentTypeEnum;
import br.com.jonathan.domain.repository.OrderRepository;
import br.com.jonathan.domain.repository.PaymentRepository;
import br.com.jonathan.domain.service.PaymentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refund(String id) {
        if (StringUtils.isNotEmpty(id)) {
            refund(paymentRepository.findOne(id));
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void completed(PaymentEntity payment) {
        if (Objects.nonNull(payment)) {
            payment.setStatus(PaymentTypeEnum.COMPLETED);
            payment.setPayment(new Date());
            paymentRepository.save(payment);
            completed(payment.getOrder());
        }
    }

    private void refund(PaymentEntity payment) {
        if (Objects.nonNull(payment)) {
            payment.setStatus(PaymentTypeEnum.REFUNDED);
            paymentRepository.save(payment);
            completed(payment.getOrder());
        }
    }

    private void completed(OrderEntity order) {
        if (Objects.nonNull(order)) {
            order.setStatus(OrderTypeEnum.COMPLETED);
            orderRepository.save(order);
        }
    }

}
