package br.com.jonathan.domain.pattern.specification;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public final class SpecificationFactory {

    private SpecificationFactory() {
        super();
    }

    public static <D> SpecificationStatus isSatisfiedBy(D domain, List<Specification<D>> specifications) {
        SpecificationStatus status = new SpecificationStatus();
        if (CollectionUtils.isNotEmpty(specifications)) {
            for (Specification<D> specification : specifications) {
                if (!specification.predicate().test(domain)) {
                    status.dissatisfy();
                    status.addErrors(specification.getError());
                }
            }
        }
        return status;
    }

}