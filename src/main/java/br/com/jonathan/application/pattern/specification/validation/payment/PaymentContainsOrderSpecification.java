package br.com.jonathan.application.pattern.specification.validation.payment;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class PaymentContainsOrderSpecification implements Specification<PaymentEntity> {

    @Inject
    private OrderRepository repository;

    @Override
    public Predicate<PaymentEntity> predicate() {
        return payment -> isValidOrder(payment);
    }

    private boolean isValidOrder(PaymentEntity payment) {
        if (Objects.nonNull(payment) && Objects.nonNull(payment.getOrder()) && Objects.nonNull(payment.getOrder().getId())) {
            return repository.existsById(payment.getOrder().getId().toString());
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(4, "Payment is not related to a valid order!");
    }

}
