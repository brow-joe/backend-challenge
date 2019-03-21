package br.com.jonathan.application.pattern.specification.validation.order;

import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.function.Predicate;

@Service
public class PaymentPerOrderSpecification implements Specification<String> {

    @Inject
    private PaymentRepository repository;

    @Override
    public Predicate<String> predicate() {
        return id -> !existsPayment(id);
    }

    private boolean existsPayment(String id) {
        return repository.existsByOrderId(id);
    }

    @Override
    public Error getError() {
        return Error.of(2, "There is payment for this order!");
    }

}
