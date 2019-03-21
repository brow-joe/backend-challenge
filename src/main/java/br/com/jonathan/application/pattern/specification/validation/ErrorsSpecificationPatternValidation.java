package br.com.jonathan.application.pattern.specification.validation;

import br.com.jonathan.domain.pattern.specification.Specification;
import br.com.jonathan.domain.pattern.specification.SpecificationFactory;
import br.com.jonathan.domain.pattern.specification.SpecificationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

public class ErrorsSpecificationPatternValidation<D> {
    protected final List<Specification<D>> specifications;

    public ErrorsSpecificationPatternValidation(Specification<D>... specifications) {
        this.specifications = Arrays.asList(specifications);
    }

    private SpecificationStatus isSatisfiedBy(D d) {
        return SpecificationFactory.isSatisfiedBy(d, specifications);
    }

    public void validade(D d) {
        SpecificationStatus status = isSatisfiedBy(d);
        if (!status.isSatisfied()) {
            throw new ResponseStatusException(
                    HttpStatus.PRECONDITION_FAILED,
                    status.getJsonErrors()
            );
        }
    }

    public static <D> ErrorsSpecificationPatternValidation<D> of(Specification<D>... specifications) {
        return new ErrorsSpecificationPatternValidation(specifications);
    }
}