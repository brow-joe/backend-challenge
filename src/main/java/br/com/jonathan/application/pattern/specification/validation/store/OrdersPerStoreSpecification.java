package br.com.jonathan.application.pattern.specification.validation.store;

import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.function.Predicate;

@Service
public class OrdersPerStoreSpecification implements Specification<String> {

    @Inject
    private OrderRepository repository;

    @Override
    public Predicate<String> predicate() {
        return id -> !existsOrderer(id);
    }

    private boolean existsOrderer(String id) {
        return repository.existsByStoreId(id);
    }

    @Override
    public Error getError() {
        return Error.of(1, "There are orders for this store!");
    }

}
