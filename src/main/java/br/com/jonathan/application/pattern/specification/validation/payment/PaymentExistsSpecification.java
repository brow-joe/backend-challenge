package br.com.jonathan.application.pattern.specification.validation.payment;

import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.PaymentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.function.Predicate;

@Service
public class PaymentExistsSpecification implements Specification<String> {

    @Inject
    private PaymentRepository repository;

    @Override
    public Predicate<String> predicate() {
        return id -> exists(id);
    }

    private boolean exists(String id) {
        if (StringUtils.isNotEmpty(id)) {
            return repository.existsById(id);
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(9, "There is no payment for the specified id!");
    }

}