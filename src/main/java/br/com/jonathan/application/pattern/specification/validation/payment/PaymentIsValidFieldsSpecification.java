package br.com.jonathan.application.pattern.specification.validation.payment;

import br.com.jonathan.domain.entity.PaymentEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Predicate;

@Service
public class PaymentIsValidFieldsSpecification implements Specification<PaymentEntity> {

    @Override
    public Predicate<PaymentEntity> predicate() {
        return payment -> isValid(payment);
    }

    private boolean isValid(PaymentEntity payment) {
        if (Objects.nonNull(payment)) {
            return payment.isValid();
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(8, "The [status, creditCard] fields are obligatory!");
    }

}