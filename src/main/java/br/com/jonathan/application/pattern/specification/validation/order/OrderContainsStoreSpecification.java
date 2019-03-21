package br.com.jonathan.application.pattern.specification.validation.order;

import br.com.jonathan.domain.entity.OrderEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.StoreRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class OrderContainsStoreSpecification implements Specification<OrderEntity> {

    @Inject
    private StoreRepository repository;

    @Override
    public Predicate<OrderEntity> predicate() {
        return order -> isValidStore(order);
    }

    private boolean isValidStore(OrderEntity order) {
        if (Objects.nonNull(order) && Objects.nonNull(order.getStore()) && Objects.nonNull(order.getStore().getId())) {
            return repository.existsById(order.getStore().getId().toString());
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(3, "Order is not related to a valid store!");
    }

}
