package br.com.jonathan.application.pattern.specification.validation.order;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Predicate;

@Service
public class OrderIsValidFieldsSpecification implements Specification<OrderEntity> {

    @Override
    public Predicate<OrderEntity> predicate() {
        return order -> isValid(order);
    }

    private boolean isValid(OrderEntity order) {
        if (Objects.nonNull(order)) {
            return order.isValid();
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(7, "The [address, status, item{ description, price, quantity }] fields are obligatory!");
    }

}
