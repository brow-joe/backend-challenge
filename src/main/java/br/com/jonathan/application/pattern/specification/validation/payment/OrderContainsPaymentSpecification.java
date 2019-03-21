package br.com.jonathan.application.pattern.specification.validation.payment;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class OrderContainsPaymentSpecification implements Specification<PaymentEntity> {

    @Inject
    private PaymentRepository repository;

    @Override
    public Predicate<PaymentEntity> predicate() {
        return payment -> !existsPayment(payment);
    }

    private boolean existsPayment(PaymentEntity payment) {
        if (Objects.nonNull(payment) && Objects.nonNull(payment.getOrder()) && Objects.nonNull(payment.getOrder().getId())) {
            return repository.existsByOrderId(payment.getOrder().getId().toString());
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(5, "The current order already contains a payment request!");
    }

}
