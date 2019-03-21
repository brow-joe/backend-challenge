package br.com.jonathan.application.pattern.specification.validation.store;

import br.com.jonathan.domain.entity.StoreEntity;
import br.com.jonathan.domain.pattern.specification.Error;
import br.com.jonathan.domain.pattern.specification.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Predicate;

@Service
public class StoreIsValidFieldsSpecification implements Specification<StoreEntity> {

    @Override
    public Predicate<StoreEntity> predicate() {
        return store -> isValid(store);
    }

    private boolean isValid(StoreEntity store) {
        if (Objects.nonNull(store)) {
            return store.isValid();
        }
        return false;
    }

    @Override
    public Error getError() {
        return Error.of(6, "The [name, address] fields are obligatory!");
    }

}
